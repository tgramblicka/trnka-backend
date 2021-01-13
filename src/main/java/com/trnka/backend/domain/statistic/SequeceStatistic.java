package com.trnka.backend.domain.statistic;

import com.trnka.backend.domain.BaseEntity;
import com.trnka.backend.domain.Student;
import com.trnka.restapi.dto.SequenceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "examination_statistic")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
@EqualsAndHashCode
public abstract class SequeceStatistic extends BaseEntity {


    @NotNull
    @Column(name = "sequence_type")
    @Enumerated(value = EnumType.STRING)
    private SequenceType sequenceType;

    // bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "student_id", nullable = false)
    @NotNull
    private Student student;

    private Boolean passed;

    @Column(name = "finished_on")
    private LocalDateTime finishedOn;

}
