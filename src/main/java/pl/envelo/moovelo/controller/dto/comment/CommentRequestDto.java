package pl.envelo.moovelo.controller.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;

import javax.mail.Multipart;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private BasicUserDto basicUser;
    @NotNull(message = "You can't add comment without message")
    @NotEmpty(message = "You can't add comment without message")
    private String text;

    @Size(max = 10)
    private List<MultipartFile> files;
}
