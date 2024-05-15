package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.dto.ChangePasswordRequest;
import fr.openclassrooms.MDD.dto.UpdateUserRequest;
import fr.openclassrooms.MDD.dto.UserDto;
import fr.openclassrooms.MDD.models.User;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsService userDetailsService() {
        return emailOrUsername -> userRepository
                .findByEmailOrUsername(emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public User save(User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public UserDto getAuthenticatedUser(UserDetails userDetails) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .interestedTopics(user.getInterestedTopics())
                .created_at(user.getCreatedAt())
                .updated_at(user.getUpdatedAt())
                .build();
    }

    public UserDto updateUser(UserDetails userDetails, UpdateUserRequest updateUserRequest) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user = userRepository.save(user);

        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .interestedTopics(user.getInterestedTopics())
                .created_at(user.getCreatedAt())
                .updated_at(user.getUpdatedAt())
                .build();
    }

    public UserDto changePassword(UserDetails userDetails, ChangePasswordRequest request) {
        var user = userRepository.findByEmailOrUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
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
