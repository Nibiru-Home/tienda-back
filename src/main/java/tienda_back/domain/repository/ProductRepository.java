package tienda_back.domain.repository;

import java.util.List;
import java.util.Optional;

import tienda_back.domain.model.Category;
import tienda_back.domain.model.Product;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(Category category);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    void deleteById(Long id);
    boolean existsById(Long id);
    Product update(Product product);
}
