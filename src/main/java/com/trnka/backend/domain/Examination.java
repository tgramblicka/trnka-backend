package com.trnka.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "examination_id")
    private List<ExaminationStep> examinationSteps = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void addExaminationStep(ExaminationStep step) {
        this.getExaminationSteps().add(step);
    }

}
