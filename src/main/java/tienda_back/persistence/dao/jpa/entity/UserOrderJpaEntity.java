package tienda_back.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;

@Entity
@Table(name = "user_orders")
public class UserOrderJpaEntity {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    private Double total;
    private Date date;
    private String status;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private CartJpaEntity cart;

    public UserOrderJpaEntity() {
    }

    public UserOrderJpaEntity(String id, UserJpaEntity user, Double total, Date date, String status,
            CartJpaEntity cart) {
        this.id = id;
        this.user = user;
        this.total = total;
        this.date = date;
        this.status = status;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CartJpaEntity getCart() {
        return cart;
    }

    public void setCart(CartJpaEntity cart) {
        this.cart = cart;
    }
}
