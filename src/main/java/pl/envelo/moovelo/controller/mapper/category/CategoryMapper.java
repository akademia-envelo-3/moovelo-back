package pl.envelo.moovelo.controller.mapper.category;

import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.controller.dto.category.CategoryListResponseDto;
import pl.envelo.moovelo.entity.categories.Category;

public class CategoryMapper {
    public static CategoryDto mapCategoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .isVisible(category.getVisible())
                .build();
    }

    public static Category mapCategoryDtoToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setVisible(categoryDto.isVisible());
        return category;
    }

    public static Category mapCategoryResponseDtoToCategory(CategoryListResponseDto categoryListResponseDto) {
        Category category = new Category();
        category.setId(categoryListResponseDto.getId());
        category.setName(categoryListResponseDto.getName());
        category.setVisible(true);
        return category;
    }
}
