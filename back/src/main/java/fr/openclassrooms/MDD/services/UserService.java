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

    /**
     * userDetailsService returns a UserDetailsService object that receives an email or username and returns a UserDetails object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method throws a UsernameNotFoundException if the user is not found.
     * @return UserDetailsService - The object that receives an email or username and returns a UserDetails object.
     * */
    public UserDetailsService userDetailsService() {
        return emailOrUsername -> userRepository
                .findByEmailOrUsername(emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    /**
     * save receives a User object and saves it in the database.
     * The method sets the createdAt field to the current date if the user is new.
     * The method sets the updatedAt field to the current date.
     * The method returns the saved User object.
     * @param newUser - The user to save in the database.
     * @return User - The saved user.
     * */
    public User save(User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    /**
     * getAuthenticatedUser receives a UserDetails object and returns a UserDto object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method throws a UsernameNotFoundException if the user is not found.
     * The method returns a UserDto object with the user information.
     * @param userDetails - The object containing the user information.
     * @return UserDto - The response object containing the user information.
     * */
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

    /**
     * updateUser receives a UserDetails object and an UpdateUserRequest object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method updates the user information with the information provided in the request.
     * The user is saved in the database using the userRepository bean.
     * The method returns a UserDto object with the updated user information.
     * @param userDetails - The object containing the user information.
     * @param updateUserRequest - The request object containing the new user information.
     * @return UserDto - The response object containing the user information.
     * */
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

    /**
     * changePassword receives a UserDetails object and a ChangePasswordRequest object.
     * The method retrieves the user from the database using the userRepository bean.
     * The method checks if the old password is correct.
     * The method encodes the new password and saves it in the database.
     * The method returns a UserDto object with the updated user information.
     * @param userDetails - The object containing the user information.
     * @param request - The request object containing the old and new passwords.
     * @return UserDto - The response object containing the user information.
     * */
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
