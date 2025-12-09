package tienda_back.domain.dto;

import java.util.List;
import tienda_back.domain.model.Style;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price,
        int stock,
        List<CategoryDto> category,
        List<Style> styles) {

}
