package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.attachment.AttachmentResponseDto;
import pl.envelo.moovelo.controller.mapper.attachment.AttachmentMapper;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.model.SortingAndPagingCriteria;
import pl.envelo.moovelo.service.AttachmentService;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class AttachmentController {

    private AttachmentService attachmentService;

    @GetMapping("{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") String fileId) {
        log.info("AttachmentController - downloadFile() - file id = '{}'", fileId);
        Attachment attachment = attachmentService.getAttachmentById(fileId);

        log.info("AttachmentController - downloadFile() - file with id = '{}' and name = '{}' send", fileId, attachment.getFileName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<AttachmentResponseDto>> getAttachmentsInfo(
            SortingAndPagingCriteria sortingAndPagingCriteria
    ) {
        log.info("AttachmentController - getAttachmentsInfo(sortingAndPagingCriteria = '{}')", sortingAndPagingCriteria);
        Page<Attachment> attachments = attachmentService.getAttachments(sortingAndPagingCriteria);
        Page<AttachmentResponseDto> attachmentResponsesDto = attachments.map(AttachmentMapper::mapAttachmentToAttachmentResponseDto);
        attachmentResponsesDto.forEach(pageAttachments -> pageAttachments.setDownloadLink(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(pageAttachments.getId())
                                .toUri()
                                .toString()
                )
        );

        log.info("AttachmentController - getAttachmentsInfo(sortingAndPagingCriteria = '{}') - return attachmentResponsesDto = '{}'",
                sortingAndPagingCriteria, attachmentResponsesDto);
        return ResponseEntity.ok(attachmentResponsesDto);
    }
}
