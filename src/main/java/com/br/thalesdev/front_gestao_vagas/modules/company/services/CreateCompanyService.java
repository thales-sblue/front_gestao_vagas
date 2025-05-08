package com.br.thalesdev.front_gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.thalesdev.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCompanyService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;

    public String execute(CreateCompanyDTO createCompanyDTO) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateCompanyDTO> request = new HttpEntity<>(createCompanyDTO, headers);

        String url = hostAPIGestaoVagas.concat("/company/");

        var result = rt.postForObject(url, request, String.class);

        return result;
    }

}
