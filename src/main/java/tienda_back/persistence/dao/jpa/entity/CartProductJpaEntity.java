package tienda_back.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_products")
public class CartProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartJpaEntity cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    public CartProductJpaEntity() {
    }

    public CartProductJpaEntity(Integer id) {
        this.id = id;
    }

    public CartProductJpaEntity(Integer id, Integer quantity, CartJpaEntity cart, ProductJpaEntity product) {
        this.id = id;
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CartJpaEntity getCart() {
        return cart;
    }

    public void setCart(CartJpaEntity cart) {
        this.cart = cart;
    }

    public ProductJpaEntity getProduct() {
        return product;
    }

    public void setProduct(ProductJpaEntity product) {
        this.product = product;
    }
}
