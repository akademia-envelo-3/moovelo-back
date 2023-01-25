package pl.envelo.moovelo.controller.dto.category;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.actor.BasicUserDto;

@Builder
@Getter
public class CategoryProposalDto {
    private long id;
    private BasicUserDto basicUser;
    private String name;
    private String description;
    private String date;
}
