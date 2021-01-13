package com.trnka.backend.domain.statistic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.trnka.backend.domain.Examination;
import com.trnka.backend.domain.ExaminationStep;
import com.trnka.backend.domain.Student;
import com.trnka.restapi.dto.SequenceType;
import com.trnka.restapi.dto.statistics.Evaluate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@DiscriminatorValue(value = "EX")
public class ExaminationStatistic extends SequeceStatistic {

    // unidirectional !
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "examination_id", nullable = false)
    private Examination examination;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "examinationStatistic")
    private List<ExaminationStepStatistic> examinationStepStatistics = new ArrayList<>();

    public static ExaminationStatistic create(final Examination examination,
                                              final Student student,
                                              final SequenceType sequenceType,
                                              final LocalDateTime finishedOn) {
        ExaminationStatistic stats = new ExaminationStatistic();
        stats.setExamination(examination);
        student.getSequenceStatistics().add(stats);
        stats.setStudent(student);
        stats.setSequenceType(sequenceType);
        stats.setFinishedOn(finishedOn);
        return stats;
    }

    public ExaminationStepStatistic addStepStatistic(final ExaminationStep step,
                                                     final long took,
                                                     final Evaluate evaluationOfSequence) {
        ExaminationStepStatistic stepStats = new ExaminationStepStatistic();
        stepStats.setStep(step);
        stepStats.setTook(took);
        stepStats.setCorrect(evaluationOfSequence.getCorrect());
        stepStats.setRetries(evaluationOfSequence.getNegativeTries());
        stepStats.setExaminationStatistic(this);
        this.getExaminationStepStatistics().add(stepStats);
        return stepStats;
    }

}
