package fr.openclassrooms.MDD.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String content;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    Topic topic;

    @ManyToOne
    User user;

    @OneToMany
    List<Comment> comments;
}
