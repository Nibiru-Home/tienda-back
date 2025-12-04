package tienda_back.domain.mapper;

import tienda_back.domain.model.product;
import tienda_back.domain.dto.productDto;
import java.util.ArrayList;

public class ProductMapper {
    private static ProductMapper INSTANCE;

    private ProductMapper() {}

    public static ProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductMapper();
        }
        return INSTANCE;
    }

    public productDto productToProductDto(product product) {
        if (product == null) {
            return null;
        }

        // Tomar la primera categoría si existe, o null
        tienda_back.domain.model.category category = null;
        if (product.getCategories() != null && !product.getCategories().isEmpty()) {
            category = product.getCategories().get(0);
        }

        return new productDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                category
        );
    }

    public product productDtoToProduct(productDto productDto) {
        if (productDto == null) {
            return null;
        }

        product product = new product();
        product.setId(productDto.id());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setStock(productDto.stock());

        // Crear lista de categorías con la categoría del DTO
        if (productDto.category() != null) {
            ArrayList<tienda_back.domain.model.category> categories = new ArrayList<>();
            categories.add(productDto.category());
            product.setCategories(categories);
        } else {
            product.setCategories(new ArrayList<>());
        }

        return product;
    }
}
