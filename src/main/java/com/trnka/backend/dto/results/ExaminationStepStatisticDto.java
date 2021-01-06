package com.trnka.backend.dto.results;

import lombok.Data;

@Data
public class ExaminationStepStatisticDto {
    private String letter;
    private Long durationInMs;
    private Integer retries;
    private Boolean correct;
    private Integer displayOrder;
}
