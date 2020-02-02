package com.trnka.backend.dto;

import com.trnka.backend.domain.BrailCharacter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExaminationStepCreateDto {

    private Long examinationId;
    private Long selectedBrailId;
    private Boolean preserveOrder;
    private List<BrailCharacter> brails;

}
