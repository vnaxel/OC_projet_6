package fr.openclassrooms.MDD.controllers;

import fr.openclassrooms.MDD.dto.SignInRequest;
import fr.openclassrooms.MDD.dto.SignUpRequest;
import fr.openclassrooms.MDD.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request, HttpServletResponse response) {
        Cookie cookie = authenticationService.signup(request);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> signin(@RequestBody SignInRequest request, HttpServletResponse response) {
        Cookie cookie = authenticationService.signin(request);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }
}
