package pl.envelo.moovelo.controller.dto.attachment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentResponseDto {
    private Long id;
    private String fileName;
    private String fileType;
    private long fileSize;
    private String downloadLink;
}
