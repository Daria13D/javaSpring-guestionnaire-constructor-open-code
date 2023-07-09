package ru.arharova.questionnarie_constructor.controllers;

import ru.arharova.questionnarie_constructor.exception.NoSuchCountExeption;
import ru.arharova.questionnarie_constructor.models.Role;
import ru.arharova.questionnarie_constructor.models.Status;
import ru.arharova.questionnarie_constructor.models.User;
import ru.arharova.questionnarie_constructor.repos.UserRepo;
import ru.arharova.questionnarie_constructor.JwtTokenProwider.TokenProviderJwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private TokenProviderJwt jwtTokenProvider;


    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepo userRepo, TokenProviderJwt jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

 /** Регистрация*/
    @PostMapping("/signup")
    public ResponseEntity<String> create(@RequestBody User user) {
        user.setPassword(String.valueOf(new BCryptPasswordEncoder(12).encode(user.getPassword())));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /** авторизация*/
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name(), user.getUsername(), String.valueOf(user.getId()));
            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new NoSuchCountExeption("Invalid email/password combination");
        }
    }

    /** выход*/
    @PostMapping("/signout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}