package tienda_back.domain.model;

import java.util.Date;

public class Cart {
    private Long id;
    private Float total;
    private Float price;
    private Date date;
    private String status;
    private User user;

    public Cart() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", total=" + total +
                ", price=" + price +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
