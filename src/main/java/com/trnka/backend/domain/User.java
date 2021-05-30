package com.trnka.backend.domain;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

    private Boolean enabled;

    // updatable=false is here for a reason. It removes all from many relation on set.clear()
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    private Set<UserAuthority> authorities = new HashSet<>();

    public void addAuthority(UserType type) {
        /// admin will get all the roles available
        if (UserType.ADMIN.equals(type)) {
            EnumSet.allOf(UserType.class)
                    .forEach(roleType -> authorities.add(new UserAuthority(getId(),
                            roleType)));
            return;
        }
        authorities.add(new UserAuthority(getId(),
                type));
    }

}
