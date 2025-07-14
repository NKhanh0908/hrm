package com.project.hrm.document.mapper;

import com.project.hrm.document.dto.documentApproverDTO.DocumentApproverCreateDTO;
import com.project.hrm.document.dto.documentApproverDTO.DocumentApproverDTO;
import com.project.hrm.document.entity.DocumentApprover;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentApproverMapper {
    public DocumentApproverDTO covertEntityToDTO(DocumentApprover documentApprover) {
        return DocumentApproverDTO.builder()
                .id(documentApprover.getId())
                .documentId(documentApprover.getDocument().getId())
                .approverId(documentApprover.getApprover().getId())
                .canApprove(documentApprover.isCanApprove())
                .build();
    }

    public List<DocumentApproverDTO> convertPageToListDTO(List<DocumentApprover> documentApproverList) {
        return documentApproverList.stream()
                .map(this::covertEntityToDTO)
                .collect(Collectors.toList());
    }
}
