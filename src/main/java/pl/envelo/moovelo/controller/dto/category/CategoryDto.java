package pl.envelo.moovelo.controller.dto.category;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryDto {
    private Long id;
    private String name;
}
