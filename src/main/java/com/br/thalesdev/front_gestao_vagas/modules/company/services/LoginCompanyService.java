package com.br.thalesdev.front_gestao_vagas.modules.company.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.br.thalesdev.front_gestao_vagas.modules.candidate.dto.Token;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginCompanyService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;

    public Token execute(String username, String password) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

        String url = hostAPIGestaoVagas.concat("/company/auth");

        var result = rt.postForObject(url, request, Token.class);

        return result;
    }

}
