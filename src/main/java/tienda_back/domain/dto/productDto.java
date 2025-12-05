package tienda_back.domain.dto;

import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price,
        int stock,
        List<CategoryDto> category) {

}
