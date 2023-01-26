package pl.envelo.moovelo.controller.dto.category;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryListResponseDto {
    private Long id;
    private String name;
}
