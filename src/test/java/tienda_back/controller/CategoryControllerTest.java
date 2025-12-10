package tienda_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.model.Category;
import tienda_back.domain.service.CategoryService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Electronics");

    }

    @Nested
    class GetAllCategoriesTests {
        @Test
        void getAllCategories_ShouldReturnCategories_WhenCategoriesExist() throws Exception {
            List<Category> categories = Collections.singletonList(category);
            when(categoryService.getAll()).thenReturn(categories);

            mockMvc.perform(get("/api/categories"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Electronics"));
        }
    }

    @Nested
    class GetCategoryByIdTests {
        @Test
        void getCategoryById_ShouldReturnCategory_WhenCategoryExists() throws Exception {
            when(categoryService.getById(1L)).thenReturn(category);

            mockMvc.perform(get("/api/categories/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Electronics"));
        }
    }

    @Nested
    class CreateCategoryTests {
        @Test
        void createCategory_ShouldReturnCreatedCategory_WhenValidInput() throws Exception {
            CategoryDto inputDto = new CategoryDto(null, "New Category");
            Category createdCategory = new Category(1L, "New Category");

            when(categoryService.create(any(Category.class))).thenReturn(createdCategory);

            mockMvc.perform(post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("New Category"));
        }
    }

    @Nested
    class UpdateCategoryTests {
        @Test
        void updateCategory_ShouldReturnUpdatedCategory_WhenValidInput() throws Exception {
            Long id = 1L;
            CategoryDto inputDto = new CategoryDto(id, "Updated Category");
            Category updatedCategory = new Category(id, "Updated Category");

            when(categoryService.update(any(Category.class))).thenReturn(updatedCategory);

            mockMvc.perform(put("/api/categories/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Updated Category"));
        }

    }

    @Nested
    class DeleteCategoryByIdTests {
        @Test
        void deleteCategoryById_ShouldReturnNoContent_WhenCategoryExists() throws Exception {
            Long id = 1L;
            doNothing().when(categoryService).deleteById(id);

            mockMvc.perform(delete("/api/categories/{id}", id))
                    .andExpect(status().isNoContent());
        }
    }
}
