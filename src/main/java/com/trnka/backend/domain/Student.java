package com.trnka.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import com.trnka.backend.domain.statistic.SequeceStatistic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Student extends BaseEntity {

    @Pattern(regexp="[1-6]+", message = "Prihlasovaci kod musi obsahovat iba cislice 1-6")
    @Length(min = 4, max = 4, message = "Prihlasovaci kod musi obsahovat presne 4 cislice!")
    private String deviceIdentificationCode;
    private Integer deviceLoginCount;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SequeceStatistic> sequenceStatistics = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private User createdBy;

}
