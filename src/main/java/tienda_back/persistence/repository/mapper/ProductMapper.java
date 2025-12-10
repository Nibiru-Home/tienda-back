package tienda_back.persistence.repository.mapper;

import tienda_back.domain.model.Product;
import tienda_back.persistence.dao.jpa.entity.ProductJpaEntity;

public class ProductMapper {

    private static ProductMapper INSTANCE;

    private ProductMapper() {
    }

    public static ProductMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductMapper();
        }
        return INSTANCE;
    }

    public Product productJpaEntityToProduct(ProductJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        Product product = new Product();
        product.setId(jpaEntity.getId().longValue());
        product.setName(jpaEntity.getName());
        product.setDescription(jpaEntity.getDescription());
        product.setPrice(jpaEntity.getPrice());
        product.setStock(jpaEntity.getStock());
        if (jpaEntity.getCategories() != null) {
            product.setCategories(jpaEntity.getCategories().stream()
                    .map(CategoryMapper.getInstance()::categoryJpaEntityToCategory)
                    .toList());
        } else {
            product.setCategories(new java.util.ArrayList<>());
        }
        return product;
    }

    public ProductJpaEntity productToProductJpaEntity(Product domain) {
        if (domain == null) {
            return null;
        }
        ProductJpaEntity entity = new ProductJpaEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId().intValue());
        }
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setStock(domain.getStock());
        if (domain.getCategories() != null) {
            entity.setCategories(domain.getCategories().stream()
                    .map(CategoryMapper.getInstance()::categoryToCategoryJpaEntity)
                    .toList());
        }
        return entity;
    }
}
