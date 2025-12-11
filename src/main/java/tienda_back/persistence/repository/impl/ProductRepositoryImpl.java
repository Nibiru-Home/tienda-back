package tienda_back.persistence.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tienda_back.domain.model.Category;
import tienda_back.domain.model.Product;
import tienda_back.domain.repository.ProductRepository;
import tienda_back.persistence.dao.jpa.ProductJpaDao;
import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;
import tienda_back.persistence.repository.mapper.ProductMapper;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaDao productJpaDao;

    public ProductRepositoryImpl(ProductJpaDao productJpaDao) {
        this.productJpaDao = productJpaDao;
    }

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = ProductMapper.getInstance().productToProductJpaEntity(product);
        if (product.getId() == null) {
            return ProductMapper.getInstance().productJpaEntityToProduct(productJpaDao.insert(entity));
        } else {
            return ProductMapper.getInstance().productJpaEntityToProduct(productJpaDao.update(entity));
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaDao.findById(id).map(ProductMapper.getInstance()::productJpaEntityToProduct);
    }

    @Override
    public List<Product> findAll() {
        return productJpaDao.findAll(0, 1000).stream()
                .map(ProductMapper.getInstance()::productJpaEntityToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByNameContaining(String name) {
         
        return Collections.emptyList();
    }

    @Override
    public List<Product> findByCategory(Category category) {
         
        return Collections.emptyList();
    }

    @Override
    public List<Product> findByPriceBetween(Double minPrice, Double maxPrice) {
         
        return Collections.emptyList();
    }

    @Override
    public void deleteById(Long id) {
        productJpaDao.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productJpaDao.findById(id).isPresent();
    }

    @Override
    public Product update(Product product) {
        return save(product);
    }
}
