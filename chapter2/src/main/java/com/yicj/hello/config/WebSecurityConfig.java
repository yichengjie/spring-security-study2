package com.yicj.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .and()
            .csrf().disable() ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance() ;
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("123").roles("user")
//                .and()
//                .withUser("admin").password("123").roles("admin") ;
//
//    }



    //@Configuration
    class MyMemoryUserDetailsManagerConfiguration{
        //@Bean
        public UserDetailsService userDetailsService(){
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(User.withUsername("user").password("123").roles("USER").build());
            manager.createUser(User.withUsername("admin").password("123").roles("USER","ADMIN").build());
            return manager ;
        }


        /**
         * 由于5.x版本之后默认启用了委派密码编码器,
         * 因而按照以往的方式配置内存密码将会读取异常
         * 所以需要暂时将密码编码器设置为NoOpPasswordEncoder
         * @return
         */

    }

    //@Configuration
    class MyJdbcUserDetailsManagerConfiguration{
        //@Autowired
        //private DataSource dataSource ;
        /*@Bean
        public UserDetailsService userDetailsService(){
            JdbcUserDetailsManager manager = new JdbcUserDetailsManager() ;
            manager.setDataSource(dataSource);

            if (!manager.userExists("user")){
                manager.createUser(User.withUsername("user").password("123").roles("USER").build());
            }
            if (!manager.userExists("admin")){
                manager.createUser(User.withUsername("admin").password("123").roles("USER","ADMIN").build());
            }
            return manager ;
        }*/
    }

}
