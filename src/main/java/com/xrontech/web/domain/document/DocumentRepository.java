package com.xrontech.web.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByEmployeeId(Long employeeId);

    List<Document> findAllByDocumentType(DocumentType documentType);

    List<Document> findAllByDocumentTypeAndEmployeeId(DocumentType documentType, Long employeeId);
}
