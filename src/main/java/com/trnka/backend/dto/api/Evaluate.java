package com.trnka.backend.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate {
    private Boolean correct;
    private Integer negativeTries;
}
