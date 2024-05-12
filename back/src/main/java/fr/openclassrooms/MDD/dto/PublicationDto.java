package fr.openclassrooms.MDD.dto;

import fr.openclassrooms.MDD.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {

    public Long id;
    public String title;
    public String content;
    public Topic topic;
    public UserDto author;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
    public List<CommentDto> comments;
}
