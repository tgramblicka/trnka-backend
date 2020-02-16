package com.trnka.backend.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity

public class Student extends BaseEntity {

    private String deviceIdentificationCode;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
