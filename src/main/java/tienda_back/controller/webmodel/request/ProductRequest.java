package tienda_back.controller.webmodel.request;

import tienda_back.domain.model.Style;
import java.util.List;

public record ProductRequest(
        Long id,
        String name,
        String description,
        Double price,
        int stock,
        List<CategoryRequest> category,
        List<Style> styles) {
}
