package uz.pdp.creatingrestfullwebservicetaskscodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Category;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.CategoryRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Page<Category>> getCategoriesService(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll(pageable));
    }

    public ResponseEntity<Category> getCategoryService(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(category -> ResponseEntity.status(HttpStatus.OK).body(category)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addCategoryService(Category category) {
        if (!Objects.equals(category.getDescription(), "") && !Objects.equals(category.getName(), "")) {
            boolean existsByName = categoryRepository.existsByName(category.getName());
            if (!existsByName) {
                categoryRepository.save(category);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The category has been successfully added!"));
            } else {
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Message(false, "This category " + category.getName() + "is already exists!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Please enter all fields fully!"));
        }
    }

    public ResponseEntity<Message> editCategoryService(Integer id, Category category) {
        if (!Objects.equals(category.getDescription(), "") && !Objects.equals(category.getName(), "")) {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                boolean exists = categoryRepository.existsByNameAndIdNot(category.getName(), id);
                if (!exists) {
                    Category editingCategory = optionalCategory.get();
                    editingCategory.setName(category.getName());
                    editingCategory.setDescription(category.getDescription());
                    categoryRepository.save(editingCategory);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The category has been successfully edited!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "This category name is already exists!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The category that you want to edit has not been found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(false, "Please enter all fields fully!"));
        }
    }

    public ResponseEntity<Message> deleteCategoryService(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The category has been successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The category that you want to delete has not been found!"));
        }
    }
}
