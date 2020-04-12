package com.yicj.hello.filter;

import com.yicj.hello.exception.VerificationCodeException;
import com.yicj.hello.handler.MyAuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerificationCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler =
            new MyAuthenticationFailureHandler() ;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //非登录请求不校验验证码
        if (!"/auth/form".equals(request.getRequestURI())){
            filterChain.doFilter(request,response);
        }else {
            try {
                verificationCode(request) ;
                filterChain.doFilter(request,response);
            }catch (VerificationCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
            }
        }
    }

    private void verificationCode(HttpServletRequest request) {

        String requestCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String sessionCode = (String)session.getAttribute("captcha") ;
        if (!StringUtils.isEmpty(sessionCode)){
            //随手清空验证码，无论是失败还是成功，客户端应在登录失败时刷新验证码
            session.removeAttribute("captcha");
        }
        //校验不通过，抛出异常
        if (StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(sessionCode)
            || !requestCode.equals(sessionCode)){
            throw new VerificationCodeException() ;
        }

    }
}
