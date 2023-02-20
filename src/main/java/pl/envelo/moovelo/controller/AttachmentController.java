package pl.envelo.moovelo.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.envelo.moovelo.controller.dto.ResponseDataDto;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.service.AttachmentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AttachmentController {

    private AttachmentService attachmentService;

    @PostMapping("upload")
    public ResponseEntity<ResponseDataDto> uploadFile(@RequestParam MultipartFile file) throws IOException {
        Attachment attachment = attachmentService.saveAttachment(file);
        ResponseDataDto responseDataDto = new ResponseDataDto();
        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/download")
                .path("/" + attachment.getId().toString())
                .toUriString();
        responseDataDto.setFileName(attachment.getFileName());
        responseDataDto.setFileSize(file.getSize());
        responseDataDto.setFileType(file.getContentType());
        responseDataDto.setDownloadUrl(downloadUrl);
        return ResponseEntity.ok(responseDataDto);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        Attachment attachment = attachmentService.getAttachmentById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
