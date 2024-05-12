package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.dto.UserDto;
import fr.openclassrooms.MDD.models.Topic;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final UserRepository userRepository;

    public List<Topic> getTopics() {
        return Arrays.asList(Topic.values());
    }

    public UserDto subscribeToTopic(UserDetails userDetails, Topic topic) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getInterestedTopics().add(topic);
        user = userRepository.save(user);

        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .interestedTopics(user.getInterestedTopics())
                .created_at(user.getCreatedAt())
                .updated_at(user.getUpdatedAt())
                .build();
    }

    public UserDto unsubscribeToTopic(UserDetails userDetails, Topic topic) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getInterestedTopics().remove(topic);
        user = userRepository.save(user);

        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .interestedTopics(user.getInterestedTopics())
                .created_at(user.getCreatedAt())
                .updated_at(user.getUpdatedAt())
                .build();
    }
}
