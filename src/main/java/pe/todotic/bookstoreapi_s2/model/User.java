package pe.todotic.bookstoreapi_s2.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "fullname")
    private String fullName;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
        this.fullName = firstName+" "+lastName;
    }

    @PreUpdate
    public void updatedAt() {
        this.updatedAt = LocalDateTime.now();
        this.fullName = firstName+" "+lastName;
    }



    public enum Role {
        ADMIN,//0
        USER//1
    }
}
