package com.xrontech.web.domain.complaint;

import com.xrontech.web.domain.model.BaseEntity;
import com.xrontech.web.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "\"Complaint\"")
public class Complaint extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "primary_sequence", sequenceName = "primary_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_sequence")
    private Long id;
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "compliance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintType complaintType;
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintStatus complaintStatus;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User employee;
}