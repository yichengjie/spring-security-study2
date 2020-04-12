package com.yicj.hello.config;

import com.yicj.hello.handler.MyAuthenticationFailureHandler;
import com.yicj.hello.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService userDetailService ;
    @Autowired
    private DataSource dataSource ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl() ;
        jdbcTokenRepository.setDataSource(dataSource);
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
//        .rememberMe()
//            .userDetailsService(userDetailService)
//            .key("blurooo");
        .rememberMe()
              .userDetailsService(userDetailService)
              .tokenRepository(jdbcTokenRepository)
              .and()
        .logout()
        //指定接受注销请求的路由
        .logoutUrl("/myLogout")
        //注销成功，重定向到该路径下
        .logoutSuccessUrl("/")
        //注销成功处理方式，不同于logoutSuccessUrl的重定向，logoutSuccessHandler更加灵活
        .logoutSuccessHandler((request,response,authentication) ->{
            System.out.println("注销成功.."); //具体注销成功执行的逻辑
        })
        //使该用户的HttpSession失效
        .invalidateHttpSession(true)
        //注销成功，删除指定cookie
        .deleteCookies("cookie1","cookie2")
        //用户注销的处理句柄，允许自定义一些清理策略
        //事实上LogoutSuccessHandler也能做到
        .addLogoutHandler( (request,response,authentication) ->{
            //注销业务
        })
        ;
    }

}
