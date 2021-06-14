package com.trnka.backend.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "authorities")
public class UserAuthority implements Serializable {
    private static final String ROLE_PREFIX = "ROLE_";

    @EmbeddedId
    private UserAuthorityId id;

    public UserAuthority(Long userId,
                         UserType authority) {
        this.id = new UserAuthorityId(userId,
                                      ROLE_PREFIX + authority);
    }

}
