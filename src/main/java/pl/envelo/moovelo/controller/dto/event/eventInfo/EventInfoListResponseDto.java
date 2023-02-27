package pl.envelo.moovelo.controller.dto.event.eventInfo;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.category.CategoryDto;

@Builder
@Getter
public class EventInfoListResponseDto {
    private String name;
    private CategoryDto category;
}
