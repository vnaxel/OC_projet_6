package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.models.User;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return emailOrUsername -> userRepository
                .findByEmailOrUsername(emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public User save (User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public Long count() {
        return userRepository.count();
    }
}
