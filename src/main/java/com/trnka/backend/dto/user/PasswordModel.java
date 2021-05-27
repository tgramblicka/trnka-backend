package com.trnka.backend.dto.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordModel {
    private Long id;
    private String password;
    private String infoMessage;
    private List<String> errors = new ArrayList<>();


}
