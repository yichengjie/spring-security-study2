package com.yicj.hello.exception;

import org.springframework.security.core.AuthenticationException;

public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(){
        super("图形验证码校验失败");
    }
}
