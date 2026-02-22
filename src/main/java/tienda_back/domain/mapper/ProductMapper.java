package tienda_back.domain.mapper;

import tienda_back.domain.model.Product;
import tienda_back.domain.dto.ProductDto;

import tienda_back.domain.model.Style;

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
                product.getImage(),
                product.getImages(),
                product.getCategories().stream().map(CategoryMapper.getInstance()::categoryToCategoryDto).toList(),
                product.getStyles().stream().map(Style::name).toList(),
                product.getRooms());
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
        product.setImage(productDto.image());
        if (productDto.images() != null) {
            product.setImages(productDto.images());
        }
        if (productDto.category() != null) {
            product.setCategories(
                    productDto.category().stream().map(CategoryMapper.getInstance()::categoryDtoToCategory).toList());
        }
        if (productDto.styles() != null) {
            product.setStyles(productDto.styles().stream().map(Style::valueOf).toList());
        }
        if (productDto.rooms() != null) {
            product.setRooms(productDto.rooms());
        }

        return product;
    }
}
