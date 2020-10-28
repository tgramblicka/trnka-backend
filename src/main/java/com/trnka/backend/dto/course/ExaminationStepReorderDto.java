package com.trnka.backend.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationStepReorderDto {

    private Long examinationId;
    private Long examinationStepId;

}
