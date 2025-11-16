package com.example.AE4_Seguridad.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("usuario", user != null ? user.getUsername() : "invitado");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/perfil")
    public String perfil(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("usuario", user.getUsername());
        return "perfil";
    }
}
