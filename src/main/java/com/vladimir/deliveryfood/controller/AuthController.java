package com.vladimir.deliveryfood.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
public class AuthController {

    @GetMapping("/auth/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/auth/login-error")
    public String getLoginPageError(Model model) {
        model.addAttribute("loginError", true);
        log.info("login with wrong username or password");
        return "login";
    }

    @GetMapping("/auth/success")
    public String getSuccessPage() {
        log.info("Successful login");
        return "redirect:/";
    }
}