package com.trnka.backend.dto;

import java.util.List;

import com.trnka.backend.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersListDto {
    private List<User> users;
}
