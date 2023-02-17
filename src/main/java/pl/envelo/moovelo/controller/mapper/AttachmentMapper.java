package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.entity.Attachment;
import pl.envelo.moovelo.service.CommentService;
import pl.envelo.moovelo.service.event.EventInfoService;

import java.util.ArrayList;
import java.util.List;


public class AttachmentMapper {
    private final EventInfoService eventInfoService;
    private final CommentService commentService;

    public AttachmentMapper(EventInfoService eventInfoService, CommentService commentService) {
        this.eventInfoService = eventInfoService;
        this.commentService = commentService;
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

    public List<Attachment> mapFromAttachmentDtoListToAttachmentList(List<AttachmentDto> attachmentDtoList) {

        List<Attachment> attachments = attachmentDtoList.stream()
                .map(attachmentDto -> new Attachment
                        (attachmentDto.getId()
                                , attachmentDto.getFilePath()
                                , eventInfoService.getEventInfoById(attachmentDto.getEventInfoId())
                                , commentService.getCommentById(attachmentDto.getCommentId())))
                .toList();

        return attachments;

    }

}
