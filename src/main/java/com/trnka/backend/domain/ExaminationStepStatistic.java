package com.trnka.backend.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "examination_step_statistic")
@EqualsAndHashCode
public class ExaminationStepStatistic extends BaseEntity {

    // unidirectional
    @OneToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private ExaminationStep step;

    @Column(name = "retries")
    private Integer retries;

    @Column(name = "correct")
    private Boolean correct;

    private Long took;
}
