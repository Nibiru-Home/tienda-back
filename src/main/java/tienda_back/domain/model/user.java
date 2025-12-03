package tienda_back.domain.model;

public class user {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private roleUser role;

    public user() {
    }
    
    public user(Long id, String name, String email, String password, String address, String phone, roleUser role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public roleUser getRole() {
        return role;
    }
    public void setRole(roleUser role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "user [id=" + id + ", name=" + name + ", email=" + email +  ",password=" + password + ", address=" + address + ", phone=" + phone + ", role=" + role + "]";
    }
    
    
}
