package com.example.AE4_Seguridad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/zona")
    public String zonaUsuario() {
        return "user/zona";
    }
}

