package com.yicj.hello.config;

import com.yicj.hello.handler.MyAuthenticationFailureHandler;
import com.yicj.hello.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService userDetailService ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/admin/api/**").hasRole("ADMIN")
            .antMatchers("/user/api/**").hasRole("USER")
            .antMatchers("/app/api/**","/captcha.jpg").permitAll()
            .anyRequest().authenticated()
            .and()
        .csrf().disable()
        .formLogin()
            .permitAll()
            .and()
         //增加自动登录功能，默认为散列加密
        .rememberMe()
            .userDetailsService(userDetailService)
            .key("blurooo");
    }

}
