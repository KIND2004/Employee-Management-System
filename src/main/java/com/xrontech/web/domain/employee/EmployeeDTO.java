package com.xrontech.web.domain.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String username;
    @NotBlank
    @Pattern(regexp = "^07[01245678]\\\\d{7}$")
    @Size(min = 10, max = 10)
    private String mobile;
}
