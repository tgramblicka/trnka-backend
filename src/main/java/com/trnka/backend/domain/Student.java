package com.trnka.backend.domain;

import javax.persistence.Entity;

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

}
