package pl.envelo.moovelo.controller.mapper.category;

import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.entity.categories.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CategoryMapperTest {

    @Test
    void shouldMapCategoryToCategoryDtoWithAllFields() {
        //given
        Category category = new Category();
        category.setId(1L);
        category.setName("wpsinaczka");
        category.setVisible(true);

        //when
        CategoryDto categoryDto = CategoryMapper.mapCategoryToCategoryDto(category);

        //then
        assertEquals(category, CategoryMapper.mapCategoryDtoToCategory(categoryDto));
    }

    @Test
    void shouldNotMapCategoryDtoToCategory() {
        //given
        CategoryDto categoryDto = CategoryDto.builder()
                .id(10L)
                .name("Ogniskowanie")
                .isVisible(false)
                .build();

        Category category = new Category();
        category.setId(10L);
        category.setName("Ogniskowanie");
        category.setVisible(true);

        //when
        Category categoryFromDto = CategoryMapper.mapCategoryDtoToCategory(categoryDto);

        //then
        assertNotEquals(category, categoryFromDto);
    }
}