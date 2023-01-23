package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.controller.dto.location.LocationDto;

import java.util.List;

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
    private List<AttachmentDto> attachments;
}
