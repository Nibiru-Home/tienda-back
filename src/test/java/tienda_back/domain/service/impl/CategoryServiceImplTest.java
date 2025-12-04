package tienda_back.domain.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import tienda_back.domain.exception.ResourceNotFoundException;
import tienda_back.domain.model.Category;
import tienda_back.domain.respository.CategoryRepository;
import tienda_back.domain.service.CategoryService;

public class CategoryServiceImplTest {
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Electronics"),
                new Category(2L, "Books"));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAll();

        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryByIdFound() {
        Category category = new Category(1L, "Electronics");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getById(1L);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getCategoryByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getById(1L);
        });

        assertEquals("La categoria con el id: 1 no existe", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void createCategory() {
        Category category = new Category(null, "New Category");
        Category savedCategory = new Category(1L, "New Category");

        when(categoryRepository.save(category)).thenReturn(savedCategory);

        Category result = categoryService.create(category);

        assertEquals(savedCategory, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void updateCategory() {
        Category category = new Category(1L, "Updated Category");

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryRepository.update(category)).thenReturn(category);

        Category result = categoryService.update(category);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, times(1)).update(category);
    }

    @Test
    void updateCategoryNotFound() {
        Category category = new Category(1L, "Updated Category");

        when(categoryRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.update(category);
        });

        assertEquals("La categoria con el id: 1 no existe", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, never()).update(any());
    }

    @Test
    void deleteCategoryById() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCategoryByIdNotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteById(1L);
        });

        assertEquals("La categoria con el id: 1 no existe", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, never()).deleteById(anyLong());
    }
}
