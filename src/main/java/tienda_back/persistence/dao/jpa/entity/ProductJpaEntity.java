package tienda_back.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String style;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    public ProductJpaEntity() {
    }

    public ProductJpaEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductJpaEntity(Integer id, String name, Double price, String description, String style,
            String image, CategoryJpaEntity category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.style = style;
        this.image = image;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @jakarta.persistence.ElementCollection
    @jakarta.persistence.CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @jakarta.persistence.Column(name = "image_url")
    private java.util.List<String> images = new java.util.ArrayList<>();

    public java.util.List<String> getImages() {
        return images;
    }

    public void setImages(java.util.List<String> images) {
        this.images = images;
    }

    public CategoryJpaEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryJpaEntity category) {
        this.category = category;
    }

    @ManyToMany
    @JoinTable(name = "product_rooms", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
    private java.util.List<RoomJpaEntity> rooms = new java.util.ArrayList<>();

    public java.util.List<RoomJpaEntity> getRooms() {
        return rooms;
    }

    public void setRooms(java.util.List<RoomJpaEntity> rooms) {
        this.rooms = rooms;
    }
}
