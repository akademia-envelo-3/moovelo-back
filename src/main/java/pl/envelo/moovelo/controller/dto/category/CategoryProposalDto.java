package pl.envelo.moovelo.controller.dto.category;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CategoryProposalDto {
    private long id;
    private long basicUserId;
    private String name;
    private String description;
    private LocalDateTime date;
}
