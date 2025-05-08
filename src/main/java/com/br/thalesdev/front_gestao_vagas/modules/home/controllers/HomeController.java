package com.br.thalesdev.front_gestao_vagas.modules.home.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home/home";
    }

}
