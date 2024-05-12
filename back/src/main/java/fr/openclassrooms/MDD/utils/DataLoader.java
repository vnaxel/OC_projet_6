package fr.openclassrooms.MDD.utils;

import fr.openclassrooms.MDD.dto.CommentRequest;
import fr.openclassrooms.MDD.models.Comment;
import fr.openclassrooms.MDD.models.Publication;
import fr.openclassrooms.MDD.models.Topic;
import fr.openclassrooms.MDD.models.User;
import fr.openclassrooms.MDD.repositories.CommentRepository;
import fr.openclassrooms.MDD.repositories.PublicationRepository;
import fr.openclassrooms.MDD.repositories.UserRepository;
import fr.openclassrooms.MDD.services.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User testUser = User
                    .builder()
                    .username("test")
                    .email("test@test.com")
                    .password(passwordEncoder.encode("tester"))
                    .interestedTopics(Set.of(Topic.SPRING, Topic.JAVA, Topic.JAVASCRIPT))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(null)
                    .build();
            userRepository.save(testUser);
            log.debug("created TEST user - {}", testUser);
            Publication testSpringPublication = Publication
                    .builder()
                    .title("Test spring publication")
                    .content("This is a test spring publication")
                    .user(testUser)
                    .topic(Topic.SPRING)
                    .createdAt(LocalDateTime.now().minusDays(3))
                    .updatedAt(null)
                    .build();
            publicationRepository.save(testSpringPublication);
            Publication testJavaPublication = Publication
                    .builder()
                    .title("Test java publication")
                    .content("This is a test java publication")
                    .user(testUser)
                    .topic(Topic.JAVA)
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .updatedAt(null)
                    .build();
            publicationRepository.save(testJavaPublication);
            Publication testAwsPublication = Publication
                    .builder()
                    .title("Test aws publication")
                    .content("This is a test aws publication")
                    .user(testUser)
                    .topic(Topic.AWS)
                    .createdAt(LocalDateTime.now().minusDays(2))
                    .updatedAt(null)
                    .build();
            publicationRepository.save(testAwsPublication);
        }
    }
}
