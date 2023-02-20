package pl.envelo.moovelo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDataDto {
    private String fileName;
    private String downloadUrl;
    private String fileType;
    private long fileSize;
}
