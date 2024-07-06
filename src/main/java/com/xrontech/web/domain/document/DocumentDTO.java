package com.xrontech.web.domain.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DocumentDTO {
    @NotNull
    private Long employeeId;
    @NotBlank
    private String title;
    @NotNull
    private DocumentType documentType;
}