package tienda_back.domain.mapper;

import tienda_back.domain.model.Product;
import tienda_back.domain.dto.ProductDto;

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

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategories().stream().map(CategoryMapper.getInstance()::categoryToCategoryDto).toList()
        );
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
        product.setCategories(productDto.category().stream().map(CategoryMapper.getInstance()::categoryDtoToCategory).toList());

        return product;
    }
}
