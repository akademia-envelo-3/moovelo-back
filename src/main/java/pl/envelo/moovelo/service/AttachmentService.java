package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.repository.AttachmentRepository;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AttachmentService {

    private AttachmentRepository attachmentRepository;

    public Attachment saveAttachment(MultipartFile file) throws IOException {
        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setData(file.getBytes());
        return attachmentRepository.save(attachment);
    }

    public Attachment getAttachmentById(String fileId) {
        return attachmentRepository.findById(Long.valueOf(fileId))
                .orElseThrow(() -> new ExpressionException("File not found!"));
    }
}
