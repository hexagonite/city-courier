package pl.ug.citycourier.internal.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pl.ug.citycourier.internal.security.boundary.RoleName;
import pl.ug.citycourier.internal.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleName name;
    @JsonBackReference
    @OneToMany(
            mappedBy = "role",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<User> users;

    public Role(int id, RoleName name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
