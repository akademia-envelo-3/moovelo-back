package pl.envelo.moovelo.controller.mapper.category;

import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.entity.categories.Category;

public class CategoryMapper {
    public static CategoryDto mapCategoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .isVisible(category.getVisible())
                .build();
    }

    public static Category mapCategoryDtoToCategory(CategoryDto categoryDto, long id) {
        Category category = new Category();
        category.setId(id);
        category.setName(categoryDto.getName());
        category.setVisible(categoryDto.isVisible());
        return category;
    }
}
