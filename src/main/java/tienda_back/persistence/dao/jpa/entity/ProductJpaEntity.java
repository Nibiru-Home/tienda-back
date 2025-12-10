package tienda_back.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private int stock;

    @jakarta.persistence.ManyToMany
    @jakarta.persistence.JoinTable(name = "product_categories", joinColumns = @jakarta.persistence.JoinColumn(name = "product_id"), inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "category_id"))
    private java.util.List<CategoryJpaEntity> categories;

    public ProductJpaEntity() {
    }

    public ProductJpaEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductJpaEntity(Integer id, String name, String description, Double price, int stock,
            java.util.List<CategoryJpaEntity> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categories = categories;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public java.util.List<CategoryJpaEntity> getCategories() {
        return categories;
    }

    public void setCategories(java.util.List<CategoryJpaEntity> categories) {
        this.categories = categories;
    }

}
