package pl.envelo.moovelo.controller.mapper.attachment;

import org.springframework.web.multipart.MultipartFile;
import pl.envelo.moovelo.controller.dto.attachment.AttachmentResponseDto;
import pl.envelo.moovelo.entity.Attachment;

import java.io.IOException;

public class AttachmentMapper {
    public static Attachment mapMultipartFileToAttachment(MultipartFile file) throws IOException {
        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setData(file.getBytes());
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
