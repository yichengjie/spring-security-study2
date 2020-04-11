package com.yicj.hello.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = urlRegistry.anyRequest();
        authorizedUrl.authenticated() ;

        //more
        FormLoginConfigurer<HttpSecurity> formLoginConfigurer = http.formLogin();
        formLoginConfigurer.loginPage("/myLogin.html") ;
        formLoginConfigurer.permitAll() ;

        //more
        CsrfConfigurer<HttpSecurity> csrf = http.csrf();
        csrf.disable() ;*/
        //效果与下面的等同

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                //登录页不设置访问权限
                .loginPage("/myLogin.html")
                //指定处理登录请求的路径
                .loginProcessingUrl("/login")
                //指定登录成功时的处理逻辑
                .successHandler(new MyAuthenticationSuccessHandler())
                //指定登录失败时的处理逻辑
                .failureHandler(new MyAuthenticationFailureHandler())
                .permitAll()
                .and()
            .csrf().disable() ;
    }


    //登录成功时的处理逻辑
    class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            //{"error_code":"0","message":"欢迎登录系统"}
            out.write("{\"error_code\":\"0\",\"message\":\"欢迎登录系统\"}");
        }
    }

    //登录失败时的处理逻辑
    class MyAuthenticationFailureHandler implements AuthenticationFailureHandler{

        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
                HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            PrintWriter out = response.getWriter();
            //输出失败原因
            //{"error_code":"401","name":"","message":""}
            out.write("{\"error_code\":\"401\",\"name\":\""+e.getClass().getName()+"\",\"message\":\""+e.getMessage()+"\"}");
        }
    }
}
