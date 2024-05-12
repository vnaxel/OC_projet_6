package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.dto.SignInRequest;
import fr.openclassrooms.MDD.dto.SignUpRequest;
import fr.openclassrooms.MDD.models.User;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Cookie signup(SignUpRequest request) {
        var user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return new Cookie("token", jwt);
    }

    public Cookie signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmailOrUsername(), request.getPassword()));
        var user = userService.userDetailsService().loadUserByUsername(request.getEmailOrUsername());
        var jwt = jwtService.generateToken(user);
        return new Cookie("token", jwt);
    }
}
