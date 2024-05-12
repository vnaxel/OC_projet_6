package fr.openclassrooms.MDD.utils;

import fr.openclassrooms.MDD.models.Topic;
import fr.openclassrooms.MDD.models.User;
import fr.openclassrooms.MDD.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.count() == 0) {
            User testUser = User
                    .builder()
                    .username("test")
                    .email("test@test.com")
                    .password(passwordEncoder.encode("tester"))
                    .interestedTopics(List.of(Topic.SPRING, Topic.JAVA, Topic.JAVASCRIPT))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(null)
                    .build();
            userService.save(testUser);
            log.debug("created TEST user - {}", testUser);
        }

    }
}
