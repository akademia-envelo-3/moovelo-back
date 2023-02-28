package pl.envelo.moovelo.controller.mapper.attachment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import pl.envelo.moovelo.controller.dto.attachment.AttachmentResponseDto;
import pl.envelo.moovelo.entity.Attachment;

import java.io.IOException;

@Slf4j
public class AttachmentMapper {
    public static Attachment mapMultipartFileToAttachment(MultipartFile file) {
        Attachment attachment = new Attachment();
        try {
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setFileSize(file.getSize());
            attachment.setData(file.getBytes());
        } catch (IOException e) {
            log.error("Unable to read file bytes!");
            throw new RuntimeException(e);
        }
        return attachment;
    }

    public static AttachmentResponseDto mapAttachmentToAttachmentResponseDto(Attachment attachment) {
        AttachmentResponseDto attachmentResponseDto = new AttachmentResponseDto();
        attachmentResponseDto.setId(attachment.getId());
        attachmentResponseDto.setFileName(attachment.getFileName());
        attachmentResponseDto.setFileType(attachment.getFileType());
        attachmentResponseDto.setFileSize(attachment.getFileSize());
        return attachmentResponseDto;
    }
}
