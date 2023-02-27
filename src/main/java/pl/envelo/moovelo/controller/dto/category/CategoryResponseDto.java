package pl.envelo.moovelo.controller.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;
    private boolean isVisible;
}
