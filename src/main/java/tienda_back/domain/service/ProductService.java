package tienda_back.domain.service;

import java.util.List;
import tienda_back.domain.model.Product;

public interface ProductService {
    List<Product> getAll();
    Product getById(Long id);
    Product create(Product product);
    Product update(Product product);
    void deleteById(Long id);
}
