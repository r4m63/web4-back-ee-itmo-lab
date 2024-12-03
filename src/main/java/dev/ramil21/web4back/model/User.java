package dev.ramil21.web4back.model;

import jakarta.persistence.*;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    private String salt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "creation_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationTime;

}