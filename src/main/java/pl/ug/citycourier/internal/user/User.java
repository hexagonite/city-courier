package pl.ug.citycourier.internal.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.ug.citycourier.internal.delivery.Delivery;
import pl.ug.citycourier.internal.security.entity.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String secondName;
    private String password;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @JsonIgnore
    @ManyToOne
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Delivery> sentDeliveries;

    @JsonIgnore
    @OneToMany(mappedBy = "courier")
    private Set<Delivery> servedDeliveries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Delivery> getSentDeliveries() {
        return sentDeliveries;
    }

    public void setSentDeliveries(Set<Delivery> sentDeliveries) {
        this.sentDeliveries = sentDeliveries;
    }

    public Set<Delivery> getServedDeliveries() {
        return servedDeliveries;
    }

    public void setServedDeliveries(Set<Delivery> servedDeliveries) {
        this.servedDeliveries = servedDeliveries;
    }
}
