package com.tensquare.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * admin控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id, HttpServletRequest request) {
        Identity claims = (Identity) request.getAttribute("ADMIN_CLAIMS");
        if (claims==null){
            return new Result(false,StatusCode.ACCESSERROR,"无权访问");
        }
        adminService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登陆
     *
     * @param loginMap
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginMap) {
        Admin admin = adminService.findByLoginnameAndPassword(loginMap.get("loginname"), loginMap.get("password"));
        if (admin != null) {
            //生成Token
            Identity identity = new Identity();
            identity.setId(admin.getId());
            identity.setUserName(admin.getLoginname());
            identity.setRole("ADMIN");
            identity.setIssuer("VX");
            String token = jwtUtil.createToken(identity);
            Map map = new HashMap();
            map.put("token", token);
            map.put("name", admin.getLoginname());//登陆名
            return new Result(true, StatusCode.OK, "登陆成功", map);
        } else {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");
        }
    }


}
