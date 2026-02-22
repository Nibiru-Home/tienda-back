package tienda_back.domain.dto;

import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price,
        String image,
        List<String> images,
        List<CategoryDto> category,
        List<String> styles,
        List<String> rooms) {

}
