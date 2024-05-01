package fr.openclassrooms.MDD.utils;

import fr.openclassrooms.MDD.models.Role;
import fr.openclassrooms.MDD.models.User;
import fr.openclassrooms.MDD.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.count() == 0) {
            User admin = User
                    .builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userService.save(admin);
            log.debug("created ADMIN user - {}", admin);
        }

    }
}
