package com.trnka.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
public class Course extends BaseEntity {

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Student> students = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Examination> examinations = new ArrayList<>();

    public void addExamination(final Examination examination) {
        examination.setCourse(this);
        getExaminations().add(examination);
    }

    public void addStudent(final Student student) {
        student.setCourse(this);
        getStudents().add(student);
    }

}
