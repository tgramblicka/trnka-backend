package com.trnka.backend.dto.results;

import java.util.List;

import lombok.Data;

@Data
    public class StudentStatisticListDto {
    List<StudentStatisticListItemDto> statistics;
}
