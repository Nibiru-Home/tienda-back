package tienda_back.domain.mapper;

import java.util.List;
import tienda_back.domain.dto.CategoryDto;
import tienda_back.domain.dto.ProductDto;
import tienda_back.domain.model.Product;

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

        List<CategoryDto> categories = null;
        if (product.getCategories() != null && !product.getCategories().isEmpty()) {
            categories = product.getCategories().stream()
                    .map(CategoryMapper.getInstance()::categoryToCategoryDto)
                    .toList();
        }

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                categories);
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
        if (productDto.category() != null) {
            product.setCategories(productDto.category().stream()
                    .map(CategoryMapper.getInstance()::categoryDtoToCategory)
                    .toList());
        }

        return product;
    }

}
