package com.yicj.hello.config;

import com.yicj.hello.service.MyUserDetailService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private MyUserDetailService userDetailsService ;

    public MyAuthenticationProvider(MyUserDetailService userDetailsService){
        this.userDetailsService = userDetailsService ;
    }


    /**
     *
     * @param userDetails  数据库中查询出来的用户信息
     * @param usernamePasswordAuthenticationToken  前端传入的用户信息
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        //编写更多校验逻辑
        //校验密码
        String msg = "AbstractUserDetailsAuthenticationProvider.badCredentials" ;
        if (usernamePasswordAuthenticationToken.getCredentials() ==null){
            throw new BadCredentialsException(this.messages.getMessage(msg,"密码不能为空")) ;
        }else {
            String presentedPassword = usernamePasswordAuthenticationToken.getCredentials().toString();
            if(!presentedPassword.equals(userDetails.getPassword())){
                throw new BadCredentialsException(this.messages.getMessage(msg,"密码错误")) ;
            }
        }

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(s);
    }
}
