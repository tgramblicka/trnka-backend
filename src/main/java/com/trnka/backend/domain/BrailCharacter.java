package com.trnka.backend.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class BrailCharacter extends BaseEntity {

    @Column(unique = true, name = "letter")
    private String letter;

    @Column(name = "brail_representation", columnDefinition = "json")
    @Type(type = "json")
    private List<Integer> brailRepresentation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audio_id", referencedColumnName = "id")
    private Audio audio;

}
