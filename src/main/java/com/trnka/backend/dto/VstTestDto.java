package com.trnka.backend.dto;

import lombok.Data;

@Data
public class VstTestDto {
    private Long id;
    private String name;
    private Integer level;
    private String className;
    private Boolean finalTest;

}
