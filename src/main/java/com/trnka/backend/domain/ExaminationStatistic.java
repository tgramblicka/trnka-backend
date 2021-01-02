package com.trnka.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.trnka.backend.dto.api.Evaluate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "examination_statistic")
@Getter
@Setter
@EqualsAndHashCode
public class ExaminationStatistic extends BaseEntity {

    // unidirectional !
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "examination_id", nullable = false)
    private Examination examination;

    // bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "student_id", nullable = false)
    @NotNull
    private Student student;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "examination_statistic_id", nullable = true)
    private List<ExaminationStepStatistic> examinationStepStatistics = new ArrayList<>();

    private Boolean passed;

    public static ExaminationStatistic create(final Examination examination,
                                              final Student student) {
        ExaminationStatistic stats = new ExaminationStatistic();
        stats.setExamination(examination);
        student.getExaminationStatistics().add(stats);
        stats.setStudent(student);
        return stats;
    }

    public ExaminationStepStatistic addStepStatistic(final ExaminationStatistic examinationStats,
                                                     final ExaminationStep step,
                                                     final long took,
                                                     final Evaluate evaluationOfSequence) {
        ExaminationStepStatistic stepStats = new ExaminationStepStatistic();
        stepStats.setStep(step);
        stepStats.setTook(took);
        stepStats.setCorrect(evaluationOfSequence.getCorrect());
        stepStats.setRetries(evaluationOfSequence.getNegativeTries());
        examinationStats.getExaminationStepStatistics().add(stepStats);
        return stepStats;
    }

}
