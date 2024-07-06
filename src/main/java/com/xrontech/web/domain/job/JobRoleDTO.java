package com.xrontech.web.domain.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobRoleDTO {
    @NotBlank
    private String title;
    @NotNull
    private Double salary;
    @NotNull
    private Long departmentId;
}
