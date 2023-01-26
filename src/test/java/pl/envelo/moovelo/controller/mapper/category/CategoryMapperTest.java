package pl.envelo.moovelo.controller.mapper.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.category.CategoryDto;
import pl.envelo.moovelo.entity.categories.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void shouldMapCategoryToCategoryDto() {
        //given
        Category category = new Category();
        category.setId(1L);
        category.setName("wpsinaczka");
        category.setVisible(true);

        //when
        CategoryDto categoryDto = CategoryMapper.mapCategoryToCategoryDto(category);

        //then
        Assertions.assertEquals(category.getId(), categoryDto.getId());
        Assertions.assertEquals(category.getName(), categoryDto.getName());
        Assertions.assertEquals(category.getVisible(), categoryDto.isVisible());
    }

    @Test
    void shouldMapCategoryDtoToCategory() {
        //given

        //when

        //then
    }
}