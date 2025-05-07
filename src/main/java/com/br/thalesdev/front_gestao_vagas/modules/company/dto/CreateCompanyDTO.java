package com.br.thalesdev.front_gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateCompanyDTO {

    private String name;
    private String username;
    private String email;
    private String password;
    private String description;

}
