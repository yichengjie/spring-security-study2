package com.yicj.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService ;

    @GetMapping("hello")
    public String hello(){
        return "hello, user" ;
    }
}
