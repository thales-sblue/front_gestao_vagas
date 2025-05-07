package com.br.thalesdev.front_gestao_vagas.modules.company.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.thalesdev.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;
import com.br.thalesdev.front_gestao_vagas.modules.company.services.CreateCompanyService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CreateCompanyService createCompanyService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("company", new CreateCompanyDTO());
        return "company/create";
    }

    @PostMapping("/create")
    public String save(RedirectAttributes redirectAttributes, Model model, CreateCompanyDTO company) {
        try {
            this.createCompanyService.execute(company);
            redirectAttributes.addFlashAttribute("success_message", "Conta criada com sucesso!");
        } catch (HttpClientErrorException e) {
            System.out.println("Error: " + e.getResponseBodyAsString());
            redirectAttributes.addFlashAttribute("error_message", e.getResponseBodyAsString());
        }

        redirectAttributes.addFlashAttribute("company", company);
        return "redirect:/company/create";
    }

}
