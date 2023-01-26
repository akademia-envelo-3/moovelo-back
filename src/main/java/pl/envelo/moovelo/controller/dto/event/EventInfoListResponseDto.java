package pl.envelo.moovelo.controller.dto.event;

import lombok.Builder;
import lombok.Getter;
import pl.envelo.moovelo.controller.dto.category.CategoryListResponseDto;

@Builder
@Getter
public class EventInfoListResponseDto {
    private String name;
    private CategoryListResponseDto category;
}
