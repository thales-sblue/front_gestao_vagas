package com.br.thalesdev.front_gestao_vagas.modules.company.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;

import com.br.thalesdev.front_gestao_vagas.modules.company.dto.ProfileCompanyDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileCompanyService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;

    public ProfileCompanyDTO execute(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        String url = hostAPIGestaoVagas.concat("/company/");

        try {
            var result = rt.exchange(url, HttpMethod.GET, request, ProfileCompanyDTO.class);
            System.out.println(result);
            return result.getBody();
        } catch (Unauthorized e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

}
