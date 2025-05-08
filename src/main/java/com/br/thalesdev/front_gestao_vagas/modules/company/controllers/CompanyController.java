package com.br.thalesdev.front_gestao_vagas.modules.company.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.thalesdev.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;
import com.br.thalesdev.front_gestao_vagas.modules.company.dto.CreateJobsDTO;
import com.br.thalesdev.front_gestao_vagas.modules.company.services.CreateCompanyService;
import com.br.thalesdev.front_gestao_vagas.modules.company.services.CreateJobService;
import com.br.thalesdev.front_gestao_vagas.modules.company.services.ListAllJobsByCompanyId;
import com.br.thalesdev.front_gestao_vagas.modules.company.services.LoginCompanyService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CreateCompanyService createCompanyService;
    private final LoginCompanyService loginCompanyService;
    private final CreateJobService createJobService;
    private final ListAllJobsByCompanyId listAllJobsByCompanyId;

    @GetMapping("/login")
    public String login() {
        return "company/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {

        try {
            var token = this.loginCompanyService.execute(username, password);
            var grants = token.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" +
                            role.toString().toUpperCase()))
                    .toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);
            auth.setDetails(token.getAccess_token());

            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext context = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);
            session.setAttribute("token", token);

            return "redirect:/company/jobs";

        } catch (HttpClientErrorException e) {
            redirectAttributes.addFlashAttribute("error_message", "Invalid username or password");
            return "redirect:/company/login";
        }

    }

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

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String jobs(Model model) {
        model.addAttribute("job", new CreateJobsDTO());
        model.addAttribute("jobs", listAllJobsByCompanyId.execute(getToken()));
        return "company/jobs";
    }

    @PostMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String createJobs(CreateJobsDTO jobs, RedirectAttributes redirectAttributes) {
        var result = this.createJobService.execute(jobs, getToken());
        System.out.println("Result: " + result);
        return "redirect:/company/jobs";
    }

    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }

}
