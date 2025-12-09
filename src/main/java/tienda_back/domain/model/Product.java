package tienda_back.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int stock;
    private List<Category> categories;
    private List<Style> styles;

    public Product() {
    }

    public Product(Long id, String name, String description, double price, int stock, List<Category> categories,
            List<Style> styles) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categories = (categories == null || categories.isEmpty()) ? new ArrayList<>() : categories;
        this.styles = (styles == null || styles.isEmpty()) ? new ArrayList<>() : styles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", stock="
                + stock + ", categories=" + categories + ", styles=" + styles + "]";
    }
}
