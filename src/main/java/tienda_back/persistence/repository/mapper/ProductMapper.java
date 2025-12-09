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
        return entity;
    }
}
