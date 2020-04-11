package com.yicj.hello.config;

import com.yicj.hello.exception.VerificationCodeException;
import com.yicj.hello.service.MyUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    //通过构造方法注入UserDetailService和PasswordEncoder
    public MyAuthenticationProvider(MyUserDetailService userDetailsService, PasswordEncoder encoder){
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(encoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //实现图形验证码的校验逻辑
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails)authentication.getDetails() ;
        String imageCode = details.getImageCode() ;
        String saveImageCode = details.getSaveImageCode();
        //校验图形验证码
        if (StringUtils.isEmpty(imageCode) || StringUtils.isEmpty(saveImageCode)
                || !imageCode.equals(saveImageCode)){
            throw new VerificationCodeException() ;
        }
        //调用父类方法完成密码校验
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
