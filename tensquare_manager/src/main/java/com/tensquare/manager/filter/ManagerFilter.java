package com.tensquare.manager.filter;

import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import entity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IceCola
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("Zuul过滤器");
        RequestContext requestContext= RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        String url=request.getRequestURL().toString();
        if(url.indexOf("/admin/login")>0){
            System.out.println("登陆页面"+url);
            return null;
        }
        //获取头信息
        String authHeader =(String)request.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer ") ){
            System.err.println(1);
            String token = authHeader.substring(7);
            Identity claims = null;
            try {
                claims = jwtUtil.parseToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(claims!=null){
                System.err.println(2);
                if("ADMIN".equals(claims.getRole())){
                    requestContext.addZuulRequestHeader("Authorization",authHeader);
                    System.out.println("token 验证通过，添加了头信息"+authHeader);
                    return null;
                }
            }
        }
        //终止运行
        requestContext.setSendZuulResponse(false);
        //http状态码
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF‐8");
        return null;
    }
}
