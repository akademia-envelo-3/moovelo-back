package pl.envelo.moovelo.controller.dto;

import lombok.Builder;
import pl.envelo.moovelo.entity.categories.Category;

@Builder
public class EventInfoDto {
    private long id;
    private long eventId;
    private String name;
    private String description;
    private CategoryDto category;
    private String startDate;
    private boolean isConfirmationRequired;
    private LocationDto location;
    private List<AttachementDto> attachments;

}
