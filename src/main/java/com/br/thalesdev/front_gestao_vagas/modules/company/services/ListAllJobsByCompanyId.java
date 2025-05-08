package com.br.thalesdev.front_gestao_vagas.modules.company.services;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.br.thalesdev.front_gestao_vagas.modules.candidate.dto.JobDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListAllJobsByCompanyId {

    public List<JobDTO> execute(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {
        };

        var result = rt.exchange("http://localhost:8080/company/job/", HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }

}
