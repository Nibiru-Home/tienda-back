package tienda_back.domain.dto;

import tienda_back.domain.model.Category;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price,
        int stock,
        Category category) {

}
