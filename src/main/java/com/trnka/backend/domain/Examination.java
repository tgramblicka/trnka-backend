package com.trnka.backend.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import com.trnka.restapi.dto.SequenceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Examination extends BaseEntity {

    @Pattern(regexp="[A-Za-z0-9_-]+", message = "Názov testu moze obsahovat len čislice, pismena bez diakritiky!")
    @Length(min = 2, max = 50, message = "Názov testu moze obsahovat aspon 2 znaky a najviac 50 znakov!")
    private String name;

    private String audio;
    private Long timeout = 0L; // default value, currently unused


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SequenceType type;

    private Integer allowedRetries;

    /**
     * In % 0-100
     */
    @Range(min = 0, max = 100)
    @Column(name = "passing_rate_percentage")
    private BigDecimal passingRate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "examination_id")
    @OrderBy(clause = "display_order ASC")
    private List<ExaminationStep> examinationSteps = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void addExaminationStep(ExaminationStep step) {
        Integer order = this.getExaminationSteps().size()+1;
        step.setDisplayOrder(order);
        this.getExaminationSteps().add(step);
    }


}
