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
import pl.envelo.moovelo.service.AttachmentService;

import java.io.IOException;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class AttachmentController {

    private AttachmentService attachmentService;

    // TODO: 23.02.2023 Dodaj logi do serwisów oraz mapperów.
    @PostMapping
    public ResponseEntity<AttachmentResponseDto> uploadFile(@RequestParam MultipartFile file)
            throws IOException {
        log.info("AttachmentController - uploadFile() - file name = '{}'", file.getName());
        Attachment attachment = AttachmentMapper.mapMultipartFileToAttachment(file);
        attachment = attachmentService.saveAttachment(attachment);

        AttachmentResponseDto attachmentResponseDto = AttachmentMapper.mapAttachmentToAttachmentResponseDto(attachment);
        URI path = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(attachmentResponseDto.getId())
                .toUri();
        attachmentResponseDto.setDownloadLink(path.toString());

        log.info("AttachmentController - uploadFile() - file = '{}' saved", attachmentResponseDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .location(path)
                .body(attachmentResponseDto);
    }

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
    ) {
        log.info("AttachmentController - getAttachmentsInfo()");
        return null;
    }
}
