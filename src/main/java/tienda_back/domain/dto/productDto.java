package tienda_back.domain.dto;

import tienda_back.domain.model.category;

public record productDto (
    Long id,
    String name,
    String description,
    Double price,
    Integer stock,
    category category
) {
    
}
