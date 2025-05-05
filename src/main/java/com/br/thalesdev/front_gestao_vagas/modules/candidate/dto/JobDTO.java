package com.br.thalesdev.front_gestao_vagas.modules.candidate.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private UUID id;
    private String description;
    private String benefits;
    private String level;
    private String companyId;
    private LocalDateTime createdAt;
}
