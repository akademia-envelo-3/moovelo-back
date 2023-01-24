package pl.envelo.moovelo.controller.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {
    private long id;
    private String name;
    private boolean isVisible;
}
