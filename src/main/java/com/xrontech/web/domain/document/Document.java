package com.xrontech.web.domain.document;

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
@Table(name = "\"Document\"")
public class Document extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "primary_sequence", sequenceName = "primary_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_sequence")
    private Long id;
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Column(name = "document_url", columnDefinition = "TEXT")
    private String documentUrl;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User employee;
}
