package tienda_back.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "carts")
public class CartJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float total;
    private Float price;

    @Temporal(TemporalType.DATE)
    @jakarta.persistence.Column(name = "date_cart")
    private Date date;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public CartJpaEntity() {
    }

    public CartJpaEntity(Integer id) {
        this.id = id;
    }

    public CartJpaEntity(Integer id, Float total, Float price, Date date, String status, UserJpaEntity user) {
        this.id = id;
        this.total = total;
        this.price = price;
        this.date = date;
        this.status = status;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }
}
