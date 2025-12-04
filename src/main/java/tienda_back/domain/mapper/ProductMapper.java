package tienda_back.domain.mapper;

import tienda_back.domain.model.Product;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.model.Category;
import java.util.ArrayList;

public class ProductMapper {
    private static ProductMapper INSTANCE;

    private ProductMapper() {
    }

    public static ProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductMapper();
        }
        return INSTANCE;
    }

    public ProductDto productToProductDto(Product product) {
        if (product == null) {
            return null;
        }

        // Tomar la primera categoría si existe, o null
        Category category = null;
        if (product.getCategories() != null && !product.getCategories().isEmpty()) {
            category = product.getCategories().get(0);
        }

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                category);
    }

    public Product productDtoToProduct(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDto.id());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setStock(productDto.stock());

        // Crear lista de categorías con la categoría del DTO
        if (productDto.category() != null) {
            ArrayList<Category> categories = new ArrayList<>();
            categories.add(productDto.category());
            product.setCategories(categories);
        } else {
            product.setCategories(new ArrayList<>());
        }

        return product;
    }
}
