package com.xrontech.web.domain.document;

import com.xrontech.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/document")
public class DocumentResource {
    private final DocumentService documentService;

    @PostMapping("/upload-details")
    public ResponseEntity<ApplicationResponseDTO> uploadDocumentDetails(@RequestBody DocumentDTO documentDTO) {
        return ResponseEntity.ok(documentService.uploadDocumentDetails(documentDTO));
    }

    @PutMapping("/upload-document/{id}")
    public ResponseEntity<ApplicationResponseDTO> uploadDocument(@PathVariable("id") Long documentId, @RequestBody MultipartFile file) {
        return ResponseEntity.ok(documentService.uploadDocument(file, documentId));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Document>> getDocuments() {
        return ResponseEntity.ok(documentService.getDocuments());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }

    @GetMapping("/get/employee/{employee-id}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable("employee-id") Long employeeId) {
        return ResponseEntity.ok(documentService.getDocuments(employeeId));
    }

    @GetMapping("/get/type/{employee-id}/{documentType}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable("employee-id") Long employeeId, @PathVariable("documentType") DocumentType documentType) {
        return ResponseEntity.ok(documentService.getDocuments(employeeId, documentType));
    }

    @GetMapping("/get/type/{document-type}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable("document-type") DocumentType documentType) {
        return ResponseEntity.ok(documentService.getDocuments(documentType));
    }

    @GetMapping("/get-own")
    public ResponseEntity<List<Document>> getOwnDocuments() {
        return ResponseEntity.ok(documentService.getOwnDocuments());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Document> getOwnDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.getOwnDocument(id));
    }

    @GetMapping("/get-own/type/{document-type}")
    public ResponseEntity<List<Document>> getOwnDocuments(@PathVariable("document-type") DocumentType documentType) {
        return ResponseEntity.ok(documentService.getOwnDocuments(documentType));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateDocument(@PathVariable("id") Long documentId, @RequestBody DocumentUpdateDTO documentUpdateDTO) {
        return ResponseEntity.ok(documentService.updateDocument(documentId, documentUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteDocument(@PathVariable("id") Long documentId) {
        return ResponseEntity.ok(documentService.deleteDocument(documentId));
    }
}
