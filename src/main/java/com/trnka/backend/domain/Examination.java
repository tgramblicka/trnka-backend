package com.trnka.backend.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Examination extends BaseEntity {

    private String name;
    private String audio;
    private Long timeout;
    private Long complexity;
    private ExaminationType type;
    private Integer allowedRetries;

    @OneToMany
    @JoinColumn(name = "examination_id", referencedColumnName = "id")
    private List<ExaminationStep> examinationSteps;



}
