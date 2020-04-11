package com.yicj.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

    @Autowired
    private UserDetailsService userDetailsService ;

    @GetMapping("hello")
    public String hello(){
        return "hello, admin" ;
    }
}
