package pl.envelo.moovelo.controller.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.AttachmentDto;
import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.controller.dto.category.CategoryListResponseDto;
import pl.envelo.moovelo.controller.dto.location.LocationDto;

import java.util.List;

@Builder
@Getter
public class EventInfoDto {
    private long id;
    private long eventId;
    private String name;
    private String description;
    private CategoryListResponseDto category;
    private String startDate;
    private boolean isConfirmationRequired;
    private LocationDto location;
    private List<AttachmentDto> attachments;

    @JsonProperty("isConfirmationRequired")
    public boolean isConfirmationRequired() {
        return isConfirmationRequired;
    }
}
