package com.xrontech.web.domain.payroll;

import com.xrontech.web.domain.model.BaseEntity;
import com.xrontech.web.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "\"Payroll\"")
public class Payroll extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "primary_sequence", sequenceName = "primary_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_sequence")
    private Long id;
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "bonus")
    private Double bonus;
    @Column(name = "deductions")
    private Double deductions;
    @Column(name = "net_pay", nullable = false)
    private Double netPay;
    @Column(name = "pay_date", nullable = false)
    private LocalDate payDate;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User employee;
}
