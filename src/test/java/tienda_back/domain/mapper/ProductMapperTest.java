package tienda_back.domain.mapper;

import org.junit.jupiter.api.Test;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.model.Category;
import tienda_back.domain.model.Product;
import tienda_back.domain.model.Style;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void testGetInstance_ReturnsSameInstance() {
        ProductMapper instance1 = ProductMapper.getInstance();
        ProductMapper instance2 = ProductMapper.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testProductToProductDto_WithNull_ReturnsNull() {
        assertNull(ProductMapper.getInstance().productToProductDto(null));
    }

    @Test
    void testProductDtoToProduct_WithNull_ReturnsNull() {
        assertNull(ProductMapper.getInstance().productDtoToProduct(null));
    }

    @Test
    void testProductToProductDto_MapsCorrectly() {
        Product product = new Product();
        product.setId(10L);
        product.setName("Mesa");
        product.setDescription("Mesa de comedor");
        product.setPrice(120.5);
        product.setImage("mesa.jpg");
        product.setImages(Collections.singletonList("mesa-detalles.jpg"));

        Category cat = new Category();
        cat.setId(1L);
        cat.setName("Hogar");
        product.setCategories(Collections.singletonList(cat));

        product.setStyles(Collections.singletonList(Style.MODERNO));
        product.setRooms(Collections.singletonList("Comedor"));

        ProductDto dto = ProductMapper.getInstance().productToProductDto(product);

        assertNotNull(dto);
        assertEquals(10L, dto.id());
        assertEquals("Mesa", dto.name());
        assertEquals("Mesa de comedor", dto.description());
        assertEquals(120.5, dto.price());
        assertEquals("mesa.jpg", dto.image());

        assertNotNull(dto.images());
        assertEquals("mesa-detalles.jpg", dto.images().get(0));

        assertNotNull(dto.category());
        assertEquals(1, dto.category().size());
        assertEquals(1L, dto.category().get(0).id());
        assertEquals("Hogar", dto.category().get(0).name());

        assertNotNull(dto.styles());
        assertEquals("MODERNO", dto.styles().get(0));

        assertNotNull(dto.rooms());
        assertEquals("Comedor", dto.rooms().get(0));
    }

    @Test
    void testProductDtoToProduct_MapsCorrectly() {
        CategoryDto catDto = new CategoryDto(2L, "Jardín");
        ProductDto dto = new ProductDto(
                20L,
                "Silla",
                "Silla exterior",
                45.0,
                "silla.jpg",
                List.of("silla2.jpg", "silla3.jpg"),
                Collections.singletonList(catDto),
                Collections.singletonList("VINTAGE"),
                Collections.singletonList("Terraza"));

        Product product = ProductMapper.getInstance().productDtoToProduct(dto);

        assertNotNull(product);
        assertEquals(20L, product.getId());
        assertEquals("Silla", product.getName());
        assertEquals("Silla exterior", product.getDescription());
        assertEquals(45.0, product.getPrice());
        assertEquals("silla.jpg", product.getImage());

        assertNotNull(product.getImages());
        assertEquals(2, product.getImages().size());
        assertEquals("silla2.jpg", product.getImages().get(0));

        assertNotNull(product.getCategories());
        assertEquals(1, product.getCategories().size());
        assertEquals(2L, product.getCategories().get(0).getId());
        assertEquals("Jardín", product.getCategories().get(0).getName());

        assertNotNull(product.getStyles());
        assertEquals(Style.VINTAGE, product.getStyles().get(0));

        assertNotNull(product.getRooms());
        assertEquals("Terraza", product.getRooms().get(0));
    }

    @Test
    void testProductDtoToProduct_WithNullLists_IgnoresThem() {
        ProductDto dto = new ProductDto(
                1L, "Test", "Test", 10.0, "test.jpg", null, null, null, null);

        Product product = ProductMapper.getInstance().productDtoToProduct(dto);

        assertNotNull(product);
        assertEquals("Test", product.getName());
        assertNull(product.getImages());
        
        
    }
}
