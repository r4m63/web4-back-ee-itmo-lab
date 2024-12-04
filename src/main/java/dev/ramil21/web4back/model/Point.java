package dev.ramil21.web4back.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "points")
@AllArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double x;
    private Double y;
    private Double r;
    private Boolean result;
}

