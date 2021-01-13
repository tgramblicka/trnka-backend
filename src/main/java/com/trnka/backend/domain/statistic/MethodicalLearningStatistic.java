package com.trnka.backend.domain.statistic;

import com.trnka.backend.domain.Student;
import com.trnka.restapi.dto.SequenceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@DiscriminatorValue(value = "ML")
/**
 * This is a special Statistic, because Methodical Learning Sequences are hardcoded on Device's DB, and do not have a reference to Examinations on VST.
 */
public class MethodicalLearningStatistic extends SequeceStatistic {

    private String letterSequence;
    private Long totalTimeInMs;

    public static MethodicalLearningStatistic create(final Student student) {
        MethodicalLearningStatistic stats = new MethodicalLearningStatistic();
        student.getSequenceStatistics().add(stats);
        stats.setStudent(student);
        stats.setSequenceType(SequenceType.METHODICAL);
        return stats;
    }

}
