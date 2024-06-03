package ch.ffhs.savvy.savvyspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String status;


    public User(Integer id, String username, String email, String password, String role, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
