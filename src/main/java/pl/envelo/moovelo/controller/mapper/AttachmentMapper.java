package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.service.CommentService;
import pl.envelo.moovelo.service.event.EventInfoService;

import java.util.ArrayList;
import java.util.List;


public class AttachmentMapper {


    public static AttachmentDto mapFromAttachmentToAttachmentDto(Attachment attachment) {
        AttachmentDto attachmentDto = AttachmentDto.builder()
                .id(attachment.getId())
                .eventInfoId(attachment.getEventInfo().getId())
                .commentId(attachment.getComment().getId())
                .filePath(attachment.getFilePath())
                .build();

        return attachmentDto;
    }

}
