package tienda_back.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<Category> categories;
    private List<Style> styles;
    private String image;
    private List<String> images;
    private List<String> rooms;

    public Product() {
    }

    public Product(Long id, String name, String description, double price, List<Category> categories,
            List<Style> styles, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categories = (categories == null || categories.isEmpty()) ? new ArrayList<>() : categories;
        this.styles = (styles == null || styles.isEmpty()) ? new ArrayList<>() : styles;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }
}
