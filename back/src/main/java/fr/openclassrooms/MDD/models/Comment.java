package fr.openclassrooms.MDD.models;

import fr.openclassrooms.MDD.dto.CommentDto;
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
    @JoinColumn(name="publication_id")
    Publication publication;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    public CommentDto toDto() {
        return CommentDto.builder()
                .content(this.content)
                .author(this.user.toDto())
                .build();
    }
}
