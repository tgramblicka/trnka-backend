package com.trnka.backend.dto;

import lombok.Data;

@Data
public class CreateTestDto {
    private String id;
    private String name;
    private String allowedRetries;     
}
