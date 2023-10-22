package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.dtos.responses.LoginResponseDTO;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.utils.JwtUtil;
import com.example.junioroasisbackend.dtos.requests.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.example.junioroasisbackend.repositories.UserRepository;

@RestController()
@RequestMapping(path = "/api/auth")
@CrossOrigin("*")
public class LoginController {
    // config jwt for local storage

    private final AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public LoginController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()));

            User user = userRepository.findFirstByEmail(((UserDetails) authentication.getPrincipal()).getUsername()).get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user.getEmail()))
                    .header("Access-Control-Expose-Headers", HttpHeaders.AUTHORIZATION) // to fix displaying jwt in local storage in angular
                    .body(
                            new LoginResponseDTO(user)
                    );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("email or password was wrong");
        }
    }
}
