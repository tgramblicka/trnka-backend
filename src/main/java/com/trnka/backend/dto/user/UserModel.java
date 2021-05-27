package com.trnka.backend.dto.user;

import java.util.ArrayList;
import java.util.List;

import com.trnka.backend.domain.UserType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {
    private Long id;
    private UserType type;
    private String username;
    private String infoMessage;
    private List<String> errors = new ArrayList<>();
}
