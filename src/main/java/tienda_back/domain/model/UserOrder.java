package tienda_back.domain.model;

import java.util.Date;

public class UserOrder {
    private String id;
    private User user;
    private Double total;
    private Date date;
    private String status;
    private Cart cart;

    public UserOrder() {
    }

    public UserOrder(String id, User user, Double total, Date date, String status, Cart cart) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", total=" + total +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", cart=" + cart +
                '}';
    }
}
