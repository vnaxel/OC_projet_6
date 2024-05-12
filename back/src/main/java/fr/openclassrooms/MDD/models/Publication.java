package fr.openclassrooms.MDD.models;

import fr.openclassrooms.MDD.dto.PublicationDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    String title;

    @NotNull
    String content;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    Topic topic;

    @ManyToOne
    User user;

    @OneToMany
    List<Comment> comments;

    public PublicationDto toDto() {
        return PublicationDto.builder()
                .title(this.title)
                .content(this.content)
                .author(this.user.toDto())
                .topic(this.topic)
                .created_at(this.createdAt)
                .updated_at(this.updatedAt)
                .build();
    }
}
