package com.trnka.backend.dto.results;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExaminationStatisticDto {
    private Long examinationId;
    private LocalDateTime finishedOn;
    private String letterSequence;
    private Long totalTimeInMs;
    private Boolean passed;
    private List<ExaminationStepStatisticDto> stepStatistics;
}
