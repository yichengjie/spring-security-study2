package com.yicj.hello;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.yicj.hello.mapper")
public class AutoLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoLoginApplication.class,args) ;
    }

    @GetMapping("/hello")
    public String hello(){

        return "hello" ;
    }
}
