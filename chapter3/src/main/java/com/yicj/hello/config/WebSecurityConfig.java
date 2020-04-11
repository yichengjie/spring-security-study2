package com.yicj.hello.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.yicj.hello.filter.VerificationCodeFilter;
import com.yicj.hello.handler.MyAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
            .loginPage("/myLogin.html")
            .loginProcessingUrl("/auth/form")
            .permitAll()
            .failureHandler(new MyAuthenticationFailureHandler());
        //将过滤器添加在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class) ;
    }

    @Bean
    public Producer captcha(){
        //配置图形验证码的基本参数
        Properties properties = new Properties() ;
        //图片宽度
        properties.setProperty("kaptcha.image.width","150") ;
        //图片长度
        properties.setProperty("kaptcha.image.height","50") ;
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string","0123456789") ;
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length","4") ;
        Config config = new Config(properties);
        //使用默认的图片验证码实现，当然也可以自定义实现
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha() ;
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance() ;
    }

}
