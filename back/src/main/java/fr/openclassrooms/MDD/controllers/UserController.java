package fr.openclassrooms.MDD.controllers;

import fr.openclassrooms.MDD.dto.ChangePasswordRequest;
import fr.openclassrooms.MDD.dto.JwtAuthenticationResponse;
import fr.openclassrooms.MDD.dto.UpdateUserRequest;
import fr.openclassrooms.MDD.dto.UserDto;
import fr.openclassrooms.MDD.services.AuthenticationService;
import fr.openclassrooms.MDD.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(userService.getAuthenticatedUser(userDetails));
    }

    @PutMapping("/me")
    public ResponseEntity<JwtAuthenticationResponse> updateMe(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateUserRequest request,
            HttpServletResponse response
    ) {
        UserDto userDto = userService.updateUser(userDetails, request);
        String jwt = authenticationService.updateUserAuth(request);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        return ResponseEntity.ok(userService.changePassword(userDetails, request));
    }
}
