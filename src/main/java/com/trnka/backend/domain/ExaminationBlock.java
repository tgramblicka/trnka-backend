package com.trnka.backend.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class ExaminationBlock extends BaseEntity {

    private String name;
    private String audio;
    private Long timeout;
    private Long complexity;
    private ExaminationType type;
    private Integer allowedRetries;

    @OneToMany
    @JoinColumn(name = "examination_block_id", referencedColumnName = "id")
    private List<ExaminationStep> examinationSteps;



}
