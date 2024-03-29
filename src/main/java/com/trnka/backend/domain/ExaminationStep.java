package com.trnka.backend.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExaminationStep extends BaseEntity {


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brail_character_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private BrailCharacter brailCharacter;

    private Boolean preserveOrder;

    private Integer displayOrder = 0;
}
