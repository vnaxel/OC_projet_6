package fr.openclassrooms.MDD.services;

import fr.openclassrooms.MDD.dto.JwtAuthenticationResponse;
import fr.openclassrooms.MDD.dto.SignInRequest;
import fr.openclassrooms.MDD.dto.SignUpRequest;
import fr.openclassrooms.MDD.dto.UpdateUserRequest;
import fr.openclassrooms.MDD.models.User;
import fr.openclassrooms.MDD.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    /**
     * signup receive a SignUpRequest object and creates a new user with the information provided in the request.
     * The password is encoded using the passwordEncoder bean.
     * The user is saved in the database using the userService bean.
     * The method generates a JWT token using the jwtService bean and returns a JwtAuthenticationResponse object.
     * @param request - The request object containing the user information.
     * @return JwtAuthenticationResponse - The response object containing the JWT token, the username, and the email of the user.
     * */
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userService.save(user);
        String token = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(token, user.getUsername(), user.getEmail());
    }

    /**
     * signin receives a SignInRequest object and authenticates the user using the authenticationManager bean.
     * If the authentication is successful, the method generates a JWT token using the jwtService bean.
     * The method returns a JwtAuthenticationResponse object containing the JWT token, the username, and the email of the user.
     * @param request - The request object containing the user credentials.
     *                      The object contains the email or username and the password of the user.
     * @return JwtAuthenticationResponse - The response object containing the JWT token, the username, and the email of the user.
     * */
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmailOrUsername(), request.getPassword()));
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmailOrUsername());
        User user = userRepository.findByEmailOrUsername(request.getEmailOrUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(userDetails);

        return new JwtAuthenticationResponse(token, user.getUsername(), user.getEmail());
    }

    /**
     * updateUserAuth receives an UpdateUserRequest object and updates the user information in the database.
     * The method generates a JWT token using the jwtService bean and returns a JwtAuthenticationResponse object.
     * @param request - The request object containing the new user information.
     * @return JwtAuthenticationResponse - The response object containing the JWT token, the username, and the email of the user.
     * */
    public JwtAuthenticationResponse updateUserAuth(UpdateUserRequest request) {
        var user = userRepository.findByEmailOrUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token =  jwtService.generateToken(user);
        return new JwtAuthenticationResponse(token, user.getUsername(), user.getEmail());
    }
}
