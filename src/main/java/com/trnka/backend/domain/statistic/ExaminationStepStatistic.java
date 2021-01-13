package com.trnka.backend.domain.statistic;

import com.trnka.backend.domain.BaseEntity;
import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "examination_statistic_id", nullable = false)
    private ExaminationStatistic examinationStatistic;


    @Column(name = "retries")
    private Integer retries;

    @Column(name = "correct")
    private Boolean correct;

    private Long took;
}
