package tienda_back.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tienda_back.domain.model.Category;
import tienda_back.persistence.dao.jpa.CategoryJpaDao;
import tienda_back.persistence.dao.jpa.entity.CategoryJpaEntity;
import tienda_back.persistence.repository.impl.CategoryRepositoryImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositoryImplTest {

    @Mock
    private CategoryJpaDao categoryJpaDao;

    private CategoryRepositoryImpl categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = new CategoryRepositoryImpl(categoryJpaDao);
    }

    @Test
    void testFindAll() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(categoryJpaDao.findAll(anyInt(), anyInt())).thenReturn(Collections.singletonList(entity));

        List<Category> result = categoryRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Electronics", result.get(0).getName());
    }

    @Test
    void testFindById_Found() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(categoryJpaDao.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Category> result = categoryRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(categoryJpaDao.findById(1L)).thenReturn(Optional.empty());

        Optional<Category> result = categoryRepository.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByName_Found() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");
        when(categoryJpaDao.findByName("Electronics")).thenReturn(Optional.of(entity));

        Optional<Category> result = categoryRepository.findByName("Electronics");

        assertTrue(result.isPresent());
        assertEquals("Electronics", result.get().getName());
    }

    @Test
    void testSave_New() {
        Category category = new Category(null, "Electronics");
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");

        when(categoryJpaDao.insert(any(CategoryJpaEntity.class))).thenReturn(entity);

        Category result = categoryRepository.save(category);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(categoryJpaDao).insert(any(CategoryJpaEntity.class));
    }

    @Test
    void testSave_Update() {
        Category category = new Category(1L, "Electronics");
        CategoryJpaEntity entity = new CategoryJpaEntity(1, "Electronics");

        when(categoryJpaDao.update(any(CategoryJpaEntity.class))).thenReturn(entity);

        Category result = categoryRepository.save(category);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(categoryJpaDao).update(any(CategoryJpaEntity.class));
    }

    @Test
    void testDeleteById() {
        categoryRepository.deleteById(1L);
        verify(categoryJpaDao).deleteById(1L);
    }
}
