package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.services.user.UserService;
import com.example.junioroasisbackend.dtos.requests.SignupRequestDTO;
import com.example.junioroasisbackend.dtos.responses.users.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@CrossOrigin("*")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> createUser(@RequestBody(required = true) SignupRequestDTO signupRequest) {

        if (userService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("This email is already exist" + signupRequest.getEmail(),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        UserResponseDTO createdUser = userService.createUser(signupRequest);
        if (createdUser == null) {
            return new ResponseEntity<>("User is not created, try again later",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
}
