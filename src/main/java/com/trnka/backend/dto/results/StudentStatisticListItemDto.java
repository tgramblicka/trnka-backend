package com.trnka.backend.dto.results;

import java.time.LocalDateTime;
import java.util.List;

import com.trnka.restapi.dto.SequenceType;
import com.trnka.restapi.dto.statistics.ExaminationStepStatisticDto;

import lombok.Data;

@Data
public class StudentStatisticListItemDto {

    private Long studentId;
    private Long examinationId;
    private SequenceType sequenceType;
    private LocalDateTime finishedOn;
    private String letterSequence;
    private Long totalTimeInMs;
    private Boolean passed;
    private List<ExaminationStepStatisticDto> stepStatistics;

}
