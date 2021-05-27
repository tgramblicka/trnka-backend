package com.trnka.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;
    
    private String password;

    @NotNull
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private UserType type;

}
