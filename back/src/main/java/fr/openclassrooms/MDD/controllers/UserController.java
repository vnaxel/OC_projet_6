package fr.openclassrooms.MDD.controllers;

import fr.openclassrooms.MDD.dto.ChangePasswordRequest;
import fr.openclassrooms.MDD.dto.UpdateUserRequest;
import fr.openclassrooms.MDD.dto.UserDto;
import fr.openclassrooms.MDD.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDto me(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getAuthenticatedUser(userDetails);
    }

    @PutMapping("/me")
    public UserDto updateMe(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userDetails, request);
    }

    @PutMapping("/me/password")
    public UserDto changePassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangePasswordRequest request) {
        return userService.changePassword(userDetails, request);
    }
}
