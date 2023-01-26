package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.category.CategoryListResponseDto;
import pl.envelo.moovelo.entity.categories.Category;

public class CategoryListResponseMapper {

    public static CategoryListResponseDto mapCategoryToCategoryListResponseDto(Category category) {
        return CategoryListResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
