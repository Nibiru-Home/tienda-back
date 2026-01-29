package tienda_back.controller.mapper;

import tienda_back.domain.dto.ProductDto;
import tienda_back.controller.webmodel.request.ProductRequest;
import tienda_back.controller.webmodel.response.ProductResponse;
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

    public ProductDto productRequestToProductDto(ProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }

        return new ProductDto(
                productRequest.id(),
                productRequest.name(),
                productRequest.description(),
                productRequest.price(),
                productRequest.stock(),
                productRequest.image(),
                null, // Images not yet supported in request
                productRequest.category().stream().map(CategoryMapper.getInstance()::categoryRequestToCategoryDto)
                        .toList(),
                productRequest.styles().stream().map(style -> style.name()).toList(),
                productRequest.rooms());
    }

    public ProductResponse productDtoToProductResponse(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return new ProductResponse(
                productDto.id(),
                productDto.name(),
                productDto.description(),
                productDto.price(),
                productDto.stock(),
                productDto.image(),
                productDto.images(),
                productDto.category().stream().map(CategoryMapper.getInstance()::categoryDtoToCategoryResponse)
                        .toList(),
                productDto.styles().stream().map(style -> Style.valueOf(style)).toList(),
                productDto.rooms());
    }
}
