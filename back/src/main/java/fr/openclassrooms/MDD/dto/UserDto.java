package fr.openclassrooms.MDD.dto;

import fr.openclassrooms.MDD.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public String email;
    public String username;
    public Set<Topic> interestedTopics;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}
