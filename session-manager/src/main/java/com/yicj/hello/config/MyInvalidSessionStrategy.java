package com.yicj.hello.config;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义会话过期策略
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,
         HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("session 无效");
    }
}