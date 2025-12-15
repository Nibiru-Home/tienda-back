package tienda_back.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private String style;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    public ProductJpaEntity() {
    }

    public ProductJpaEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductJpaEntity(Integer id, String name, Double price, String description, Integer stock, String style,
            CategoryJpaEntity category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.style = style;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public CategoryJpaEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryJpaEntity category) {
        this.category = category;
    }
}
