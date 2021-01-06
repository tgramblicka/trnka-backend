package com.trnka.backend.dto.results;

import java.util.List;

import com.trnka.restapi.dto.statistics.ExaminationStatisticDto;

import lombok.Data;

@Data
public class StudentStatisticListDto {
    List<ExaminationStatisticDto> statistics;
}
