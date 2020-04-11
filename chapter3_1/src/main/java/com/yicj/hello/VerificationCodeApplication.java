package com.yicj.hello;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.yicj.hello.mapper")
public class VerificationCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerificationCodeApplication.class,args) ;
    }

    @GetMapping("/hello")
    public String hello(){

        return "hello" ;
    }
}
