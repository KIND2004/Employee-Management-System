package com.xrontech.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationResponseDTO {
    private HttpStatus status;
    private String code;
    private String message;
}
