package com.shop.projectlion.global.security.handler;

import com.shop.projectlion.global.error.exception.ErrorCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class FormLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = getExceptionMessage(exception);

        setDefaultFailureUrl("/login?error=True&exception="+errorMessage);
        super.onAuthenticationFailure(request,response,exception);
    }

    private String getExceptionMessage(AuthenticationException exception) {
        if(exception instanceof UsernameNotFoundException){
            return URLEncoder.encode(ErrorCode.NOT_FOUND_MEMBER.getMessage());
        }

        if(exception instanceof BadCredentialsException){
            return URLEncoder.encode(ErrorCode.MISMATCHED_PASSWORD.getMessage());
        }
        return URLEncoder.encode(ErrorCode.LOGIN_ERROR.getMessage());
    }
}
