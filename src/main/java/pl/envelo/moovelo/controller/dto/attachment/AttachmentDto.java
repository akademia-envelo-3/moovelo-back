package pl.envelo.moovelo.controller.dto.attachment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AttachmentDto {
    private long id;
    private long eventInfoId;
    private long commentId;
    private String filePath;
}
