package pl.envelo.moovelo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AttachmentDto {
    private long id;
    private long eventInfoId;
    private long commentId;
    private String filePath;
}
