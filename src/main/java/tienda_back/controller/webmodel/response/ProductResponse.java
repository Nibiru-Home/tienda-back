package tienda_back.controller.webmodel.response;

import java.util.List;
import tienda_back.domain.model.Style;

public record ProductResponse(
                Long id,
                String name,
                String description,
                Double price,
                int stock,
                String image,
                List<CategoryResponse> category,
                List<Style> styles) {

}
