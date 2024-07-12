package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.dto.UserDto;
import fr.openclassrooms.MDD.models.Topic;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final UserRepository userRepository;

    /**
     * getTopics returns a list of all the topics available in the application.
     * The method returns a list of Topic objects.
     * @return List<Topic> - A list of Topic objects.
     * */
    public List<Topic> getTopics() {
        return Arrays.asList(Topic.values());
    }

    /**
     * subscribeToTopic receives a UserDetails object and a Topic object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method adds the topic to the user's interested topics.
     * The user is saved in the database using the userRepository bean.
     * The method returns a UserDto object.
     * @param userDetails - The object containing the user information.
     * @param topic - The topic to subscribe to.
     * @return UserDto - The response object containing the user information.
     * */
    public UserDto subscribeToTopic(UserDetails userDetails, Topic topic) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<Topic> interestedTopics = user.getInterestedTopics();
        if (interestedTopics != null) {
            interestedTopics.add(topic);
        } else {
            interestedTopics = Set.of(topic);
        }
        user.setInterestedTopics(interestedTopics);
        user = userRepository.save(user);

        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .interestedTopics(user.getInterestedTopics())
                .created_at(user.getCreatedAt())
                .updated_at(user.getUpdatedAt())
                .build();
    }

    /**
     * unsubscribeToTopic receives a UserDetails object and a Topic object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method removes the topic from the user's interested topics.
     * The user is saved in the database using the userRepository bean.
     * The method returns a UserDto object.
     * @param userDetails - The object containing the user information.
     * @param topic - The topic to unsubscribe from.
     * @return UserDto - The response object containing the user information.
     * */
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
