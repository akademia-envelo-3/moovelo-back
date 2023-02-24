package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.repository.AttachmentRepository;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AttachmentService {

    private AttachmentRepository attachmentRepository;

    public Attachment saveAttachment(Attachment attachment) throws IOException {
        return attachmentRepository.save(attachment);
    }

    public Attachment getAttachmentById(String fileId) {
        return attachmentRepository.findById(Long.valueOf(fileId))
                .orElseThrow(() -> new ExpressionException("File not found!"));
    }
}
