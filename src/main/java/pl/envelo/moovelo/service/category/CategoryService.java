package pl.envelo.moovelo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.categories.Category;
import pl.envelo.moovelo.repository.category.CategoryRepository;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category checkIfCategoryExists(Category category) {
        if (categoryRepository.findById(category.getId()).isPresent()) {
            return categoryRepository.findById(category.getId()).get();
        } else {
            throw new NoSuchElementException("Category does not exist");
        }
    }
}
