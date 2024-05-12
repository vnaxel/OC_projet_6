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
public class UserDto {

    public String email;
    public String username;
    public List<Topic> interestedTopics;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}
