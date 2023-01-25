package pl.envelo.moovelo.controller.mapper;

import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.entity.categories.Category;

public class CategoryMapper {

    public static CategoryDto mapCategoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
