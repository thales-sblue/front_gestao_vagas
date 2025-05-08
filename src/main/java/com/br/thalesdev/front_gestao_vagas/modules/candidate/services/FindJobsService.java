package com.br.thalesdev.front_gestao_vagas.modules.candidate.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.thalesdev.front_gestao_vagas.modules.candidate.dto.JobDTO;

import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindJobsService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;

    public List<JobDTO> execute(String token, String filter) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        String url = hostAPIGestaoVagas.concat("/candidate/job");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("filter", filter);

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {
        };

        try {
            var result = rt.exchange(builder.toUriString(), HttpMethod.GET, request, responseType);
            System.out.println(result);
            return result.getBody();
        } catch (Unauthorized e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

    }

}
