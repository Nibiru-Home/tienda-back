package tienda_back.domain.model;

public class CartProduct {
    private Long id;
    private int quantity;
    private Cart cart;
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", cart=" + cart +
                ", product=" + product +
                '}';
    }
}
