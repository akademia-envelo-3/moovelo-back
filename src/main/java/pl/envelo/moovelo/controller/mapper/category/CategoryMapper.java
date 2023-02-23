package pl.envelo.moovelo.controller.mapper.category;

import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.entity.categories.Category;

public class CategoryMapper {
    public static CategoryDto mapCategoryToCategoryListResponseDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category mapCategoryListDtoToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

// TODO: 23.02.2023 Tu metoda do napisania dla Admina
//    public static Category mapCategoryRequestToCategory(CategoryRequestDto categoryRequestDto) {
//        Category category = new Category();
//        category.setName(categoryRequestDto.getName());
//        category.setVisible(true);
//        return category;
//    }
}
