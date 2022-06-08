package uz.pdp.creatingrestfullwebservicetaskscodingbat.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    @NotNull(message = "Please enter your email!")
    private String email;
    @Column(nullable = false,unique = true)
    @NotNull(message = "Please enter your password!")
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
