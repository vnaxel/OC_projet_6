package fr.openclassrooms.MDD.controllers;

import fr.openclassrooms.MDD.dto.ChangePasswordRequest;
import fr.openclassrooms.MDD.dto.UpdateUserRequest;
import fr.openclassrooms.MDD.dto.UserDto;
import fr.openclassrooms.MDD.services.AuthenticationService;
import fr.openclassrooms.MDD.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Security;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getAuthenticatedUser(userDetails));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMe(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateUserRequest request, HttpServletResponse response) {
        UserDto userDto = userService.updateUser(userDetails, request);
        Cookie updatedUsercookie = authenticationService.updateUserCookie(request);
        updatedUsercookie.setPath("/");
        response.addCookie(updatedUsercookie);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/me/password")
    public ResponseEntity<UserDto> changePassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(userService.changePassword(userDetails, request));
    }
}
