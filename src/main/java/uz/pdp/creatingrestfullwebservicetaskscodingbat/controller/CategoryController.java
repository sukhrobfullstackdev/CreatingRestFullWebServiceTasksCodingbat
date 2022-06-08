package uz.pdp.creatingrestfullwebservicetaskscodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Category;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.service.CategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<Category>> getCategoriesController(@RequestParam int page) {
        return categoryService.getCategoriesService(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> getCategoryController(@PathVariable Integer id) {
        return categoryService.getCategoryService(id);
    }

    @PostMapping
    public ResponseEntity<Message> addCategoryController(@Valid @RequestBody Category category) {
        return categoryService.addCategoryService(category);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editCategoryController(@Valid @RequestBody Category category, @PathVariable Integer id) {
        return categoryService.editCategoryService(id, category);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteCategoryController(@PathVariable Integer id) {
        return categoryService.deleteCategoryService(id);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
