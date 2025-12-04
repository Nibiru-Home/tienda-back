package tienda_back.domain.respository;

import java.util.List;
import java.util.Optional;

import tienda_back.domain.model.category;
import tienda_back.domain.model.product;

public interface ProductRepository {
    product save(product product);
    Optional<product> findById(Long id);
    List<product> findAll();
    List<product> findByNameContaining(String name);
    List<product> findByCategory(category category);
    List<product> findByPriceBetween(Double minPrice, Double maxPrice);
    void deleteById(Long id);
    boolean existsById(Long id);
}
