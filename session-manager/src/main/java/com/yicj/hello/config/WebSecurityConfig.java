package com.yicj.hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
//import org.springframework.session.security.SpringSessionBackedSessionRegistry;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringSessionBackedSessionRegistry redisSessionRegistry ;

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
        .sessionManagement()
            //会话过期会跳转到这个页面
            //.invalidSessionUrl("/login")
            //自定义会话过期策略
            //.invalidSessionStrategy(new MyInvalidSessionStrategy())
            //最大会话数设置为1,当超过一个时默认会踢掉前一个登录
            .maximumSessions(1)
            //阻止新会话登录，默认为false
            //.maxSessionsPreventsLogin(true)
            //使用session提供的合法注册表
            .sessionRegistry(redisSessionRegistry)
        ;
    }




}
