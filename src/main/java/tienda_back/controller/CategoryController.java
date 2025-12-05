package tienda_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.mapper.CategoryMapper;
import tienda_back.domain.model.Category;
import tienda_back.domain.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> CategoryMapper.getInstance().categoryToCategoryDto(category))
                .toList();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        CategoryDto categoryDto = CategoryMapper.getInstance().categoryToCategoryDto(category);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = CategoryMapper.getInstance().categoryDtoToCategory(categoryDto);
        Category createdCategory = categoryService.create(category);
        CategoryDto createdCategoryDto = CategoryMapper.getInstance().categoryToCategoryDto(createdCategory);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        if (!id.equals(categoryDto.id())) {
            throw new IllegalArgumentException("ID in path and request body must match");
        }
        Category category = CategoryMapper.getInstance().categoryDtoToCategory(categoryDto);
        Category updatedCategory = categoryService.update(category);
        CategoryDto updatedCategoryDto = CategoryMapper.getInstance().categoryToCategoryDto(updatedCategory);
        return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
