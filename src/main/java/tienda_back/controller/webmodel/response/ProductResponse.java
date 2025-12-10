package tienda_back.controller.webmodel.response;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        boolean stock,
        List<CategoryResponse> category) {

}
