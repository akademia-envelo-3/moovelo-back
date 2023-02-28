package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.model.SortingAndPagingCriteria;
import pl.envelo.moovelo.repository.AttachmentRepository;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AttachmentService {

    private AttachmentRepository attachmentRepository;

    public Attachment saveAttachment(Attachment attachment) {
        log.info("AttachmentService - saveAttachment(attachment = '{}')", attachment);
        return attachmentRepository.save(attachment);
    }

    public Attachment getAttachmentById(String fileId) {
        log.info("AttachmentService - getAttachmentById(fileId = '{}')", fileId);
        return attachmentRepository.findById(Long.valueOf(fileId))
                .orElseThrow(() -> new ExpressionException("File not found!"));
    }

    public Page<Attachment> getAttachments(SortingAndPagingCriteria sortingAndPagingCriteria) {
        log.info("AttachmentService - getAttachments(sortingAndPagingCriteria = '{}')", sortingAndPagingCriteria);
        Pageable pageable = PageRequest.of(
                sortingAndPagingCriteria.getPageNumber(),
                sortingAndPagingCriteria.getPageSize(),
                Sort.by(
                        new Sort.Order(
                                sortingAndPagingCriteria.getSortDirection(),
                                sortingAndPagingCriteria.getSortBy()
                        )
                )
        );

        Page<Attachment> attachments = attachmentRepository.findAll(pageable);

        log.info("AttachmentService - getAttachments(sortingAndPagingCriteria = '{}') - return attachments = '{}'",
                sortingAndPagingCriteria, attachments);
        return attachments;
    }

    public List<Attachment> saveAttachments(List<Attachment> attachments) {
        log.info("AttachmentService - saveAttachments(attachments = '{}')", attachments);
        return attachmentRepository.saveAll(attachments);
    }
}
