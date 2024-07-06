package com.xrontech.web.domain.payroll;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayrollUpdateDTO {
    private Double bonus;
    private Double deductions;
    @NotNull
    private LocalDate payDate;
}
