package tienda_back.controller.webmodel.request;

import java.util.List;

public record ProductRequest (
    Long id,
    String name,
    String description,
    Double price,
    int stock,
    List<CategoryRequest> category
){}

