package com.trnka.backend.dto;

import com.trnka.backend.domain.Examination;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestModel {
    private Examination examination;
    private ExaminationStepCreateDto examinationStepCreateDto;
}
