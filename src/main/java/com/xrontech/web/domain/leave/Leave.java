package com.xrontech.web.domain.leave;

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
@Table(name = "\"Leave\"")
public class Leave extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "primary_sequence", sequenceName = "primary_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_sequence")
    private Long id;
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false)
    private LeaveType leaveType;
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "leave_status", nullable = false)
    private LeaveStatus leaveStatus;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User employee;
}
