package com.xrontech.web.domain.attendance;

import com.xrontech.web.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "\"Attendance\"")
public class Attendance {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime;
    @Column(name = "check_out_time")
    private LocalTime checkOutTime;
    @CreatedDate
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}