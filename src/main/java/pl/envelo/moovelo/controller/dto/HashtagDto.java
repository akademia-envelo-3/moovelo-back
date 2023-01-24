package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HashtagDto {
    private long id;
    private long eventId;
    private String value;
    private boolean isVisible;
    private int occurrences;

}
