package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "提交成功 ");
    }

    @RequestMapping(value = "/article/{articleid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByArticleid(@PathVariable String articleid, @PathVariable int page, @PathVariable int size) {
        Page<Comment> p = commentService.findByArticleid(articleid, page, size);
        PageResult<Comment> pageResult = new PageResult<>(p.getTotalElements(), p.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @DeleteMapping("/{id}")
    public Result deleteByID(@PathVariable String id) {
        commentService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}

