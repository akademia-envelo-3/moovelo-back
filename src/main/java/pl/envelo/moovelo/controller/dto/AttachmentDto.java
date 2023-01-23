package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

@Builder
public class AttachmentDto {
    private long id;
    private long eventInfoId;
    private long commentId;
    private String filePath;
}
