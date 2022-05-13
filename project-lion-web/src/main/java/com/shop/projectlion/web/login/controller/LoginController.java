package com.shop.projectlion.web.login.controller;

import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.DuplicatedUserException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import com.shop.projectlion.web.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String errorMessage,
                        Model model) {

        if(Boolean.valueOf(error)){
            model.addAttribute("message", errorMessage);
        }

        return "login/loginform";
    }




    @GetMapping("/logout")
    public String logout(Authentication authentication,
                         HttpServletRequest request,
                         HttpServletResponse response){

        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }

        return "redirect:/login";
    }





    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "login/registerform";
    }




    @PostMapping("/register")
    public String register(@Validated @ModelAttribute MemberRegisterDto memberRegisterDto,
                           BindingResult bindingResult) throws IOException {

        /* 패스워드가 일치하는지 확인하는 로직 */
        if(!memberRegisterDto.getPassword().equals(memberRegisterDto.getPassword2())){
            bindingResult.reject("differentPassword", ErrorCode.MISMATCHED_PASSWORD.getMessage());
        }

        if(bindingResult.hasErrors()){
            return "login/registerform";
        }

        try {
            loginService.registerMember(memberRegisterDto);
        }catch (DuplicatedUserException e){
            bindingResult.reject("alreadyExists", e.getMessage());
            return "login/registerform";
        }

        return "redirect:/login";
    }

}
