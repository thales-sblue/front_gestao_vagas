package com.br.thalesdev.front_gestao_vagas.modules.company.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProfileCompanyDTO {
    private UUID id;
    private String username;
    private String name;
    private String email;
    private String website;
    private String description;
}
