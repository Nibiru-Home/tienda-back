package tienda_back.persistence.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import tienda_back.domain.model.Product;
import tienda_back.domain.model.Style;
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
        product.setStock(jpaEntity.getStock() != null ? jpaEntity.getStock() : 0);
        product.setImage(jpaEntity.getImage());
        product.setImages(jpaEntity.getImages());

        // Map CategoryJpaEntity (single) to List<Category>
        if (jpaEntity.getCategory() != null) {
            product.setCategories(
                    List.of(CategoryMapper.getInstance().categoryJpaEntityToCategory(jpaEntity.getCategory())));
        } else {
            product.setCategories(new ArrayList<>());
        }

        // Map String style to List<Style>
        if (jpaEntity.getStyle() != null) {
            try {
                product.setStyles(List.of(Style.valueOf(jpaEntity.getStyle().toUpperCase())));
            } catch (IllegalArgumentException e) {
                // If style string doesn't match Enum, ignore or add log? For now empty list.
                product.setStyles(new ArrayList<>());
            }
        } else {
            product.setStyles(new ArrayList<>());
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
        entity.setImage(domain.getImage());
        entity.setImages(domain.getImages());

        // Map List<Category> (first item) to CategoryJpaEntity
        if (domain.getCategories() != null && !domain.getCategories().isEmpty()) {
            entity.setCategory(CategoryMapper.getInstance().categoryToCategoryJpaEntity(domain.getCategories().get(0)));
        }

        // Map List<Style> (first item) to String style
        if (domain.getStyles() != null && !domain.getStyles().isEmpty()) {
            entity.setStyle(domain.getStyles().get(0).name());
        }

        return entity;
    }
}
