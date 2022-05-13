package com.shop.projectlion.global.security.provider;

import com.shop.projectlion.global.security.service.FormLoginUserDetails;
import com.shop.projectlion.global.security.service.FormLoginUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final FormLoginUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        FormLoginUserDetails userDetails = (FormLoginUserDetails) userDetailsService.loadUserByUsername(email);

        isPasswordMatching(password, userDetails);

        /* 인증 성공 후 인증 토큰 반환 */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.getMember(), null, userDetails.getAuthorities());

        return authenticationToken;
    }

    private void isPasswordMatching(String password, UserDetails userDetails) {
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Before authentication token
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
