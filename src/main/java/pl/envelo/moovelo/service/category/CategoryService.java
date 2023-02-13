package pl.envelo.moovelo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.categories.Category;
import pl.envelo.moovelo.repository.category.CategoryRepository;

import javax.persistence.EntityExistsException;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category validateCategory(Category category) {
        return checkIfCategoryExistsInDb(category);
    }

    private Category checkIfCategoryExistsInDb(Category category) {
        if (categoryRepository.findById(category.getId()).isPresent()) {
            return categoryRepository.findById(category.getId()).get();
        } else {
            throw new EntityExistsException("Category does not exist");
        }
    }
}
