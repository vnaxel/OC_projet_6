package fr.openclassrooms.MDD.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "comments")
public class Comment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        String content;

        LocalDateTime createdAt;

        LocalDateTime updatedAt;

        @ManyToOne
        Publication publication;

        @ManyToOne
        User user;
}
