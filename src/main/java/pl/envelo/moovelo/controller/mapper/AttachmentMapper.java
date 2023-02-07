package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.entity.events.EventInfo;

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

    public static List<AttachmentDto> mapFromAttachmentListToAttachmentDtoList(List<Attachment> attachments) {
        List<AttachmentDto> attachmentDtoList = attachments.stream()
                .map(attachment -> new AttachmentDto
                        (attachment.getId()
                                , attachment.getEventInfo().getId()
                                , attachment.getComment().getId()
                                , attachment.getFilePath()))
                .toList();

        return attachmentDtoList;
    }

}
