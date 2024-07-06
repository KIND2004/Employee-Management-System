package com.xrontech.web.domain.document;

import com.xrontech.web.domain.security.entity.User;
import com.xrontech.web.domain.security.repos.UserRepository;
import com.xrontech.web.domain.user.UserService;
import com.xrontech.web.dto.ApplicationResponseDTO;
import com.xrontech.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private static final String INVALID_DOCUMENT_ID_CODE = "INVALID_DOCUMENT_ID";
    private static final String INVALID_DOCUMENT_ID_MSG = "Invalid Document Id";

    public ApplicationResponseDTO uploadDocumentDetails(DocumentDTO documentDTO) {
        User user = userRepository.findById(documentDTO.getEmployeeId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_USER_ID", "Invalid User Id"));
        documentRepository.save(
                Document.builder()
                        .employeeId(user.getId())
                        .title(documentDTO.getTitle())
                        .documentType(documentDTO.getDocumentType())
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "DOCUMENT_DETAILS_UPLOADED_SUCCESSFULLY", "Document Details Uploaded Successfully");
    }

    public ApplicationResponseDTO uploadDocument(MultipartFile file, Long documentId) {
        if (file.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "FILE_NOT_SELECTED", "File not Selected");
        } else {
            try {
                String projectRoot = System.getProperty("user.dir");
                String originalFilename = file.getOriginalFilename();
                if (originalFilename != null) {
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    if (!(fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg") || fileExtension.equalsIgnoreCase(".png"))) {
                        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_FILE_TYPE", "Invalid file type. Only JPG, JPEG, and PNG are allowed.");
                    }
                    String newFileName = UUID.randomUUID() + fileExtension;
                    String documentPath = "/uploads/documents/" + newFileName;
                    Path path = Paths.get(projectRoot + documentPath);
                    File saveFile = new File(String.valueOf(path));
                    file.transferTo(saveFile);
                    Document document = documentRepository.findById(documentId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_DOCUMENT_ID_CODE, INVALID_DOCUMENT_ID_MSG));
                    document.setDocumentUrl(documentPath);
                    documentRepository.save(document);
                    return new ApplicationResponseDTO(HttpStatus.CREATED, "DOCUMENT_UPLOADED_SUCCESSFULLY", "Document Uploaded Successfully");
                } else {
                    throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "ORIGINAL_FILE_NAME_NOT_FOUND", "Original File Name Not Found");
                }
            } catch (IOException e) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "FILE_NOT_SAVED", "File not Saved");
            }
        }
    }

    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_DOCUMENT_ID_CODE, INVALID_DOCUMENT_ID_MSG));
    }

    public List<Document> getDocuments(Long employeeId) {
        User user = userRepository.findById(employeeId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_USER_ID", "Invalid User Id"));
        return documentRepository.findAllByEmployeeId(user.getId());
    }

    public List<Document> getDocuments(Long employeeId, DocumentType documentType) {
        return documentRepository.findAllByDocumentTypeAndEmployeeId(documentType, employeeId);
    }

    public List<Document> getDocuments(DocumentType documentType) {
        return documentRepository.findAllByDocumentType(documentType);
    }

    public List<Document> getOwnDocuments() {
        User user = userService.getCurrentUser();
        return documentRepository.findAllByEmployeeId(user.getId());
    }

    public Document getOwnDocument(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_DOCUMENT_ID_CODE, INVALID_DOCUMENT_ID_MSG));
        User user = userService.getCurrentUser();
        if (document.getEmployeeId().equals(user.getId())) {
            return document;
        }
        throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_DOCUMENT", "Un Authorized Document.");
    }

    public List<Document> getOwnDocuments(DocumentType documentType) {
        User user = userService.getCurrentUser();
        return documentRepository.findAllByDocumentTypeAndEmployeeId(documentType, user.getId());
    }

    public ApplicationResponseDTO updateDocument(Long documentId, DocumentUpdateDTO documentUpdateDTO) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_DOCUMENT_ID_CODE, INVALID_DOCUMENT_ID_MSG));
        document.setTitle(documentUpdateDTO.getTitle());
        document.setDocumentType(documentUpdateDTO.getDocumentType());
        documentRepository.save(document);
        return new ApplicationResponseDTO(HttpStatus.OK, "DOCUMENT_UPDATED_SUCCESSFULLY", "Document Updated Successfully");
    }

    public ApplicationResponseDTO deleteDocument(Long documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, INVALID_DOCUMENT_ID_CODE, INVALID_DOCUMENT_ID_MSG));
        documentRepository.delete(document);
        return new ApplicationResponseDTO(HttpStatus.OK, "DOCUMENT_DELETED_SUCCESSFULLY", "Document Deleted Successfully");
    }
}
