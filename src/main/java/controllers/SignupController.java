package controllers;

import dtos.SignupDTO;
import dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.user.UserService;

@RestController
@RequestMapping("/sign-up")
@CrossOrigin("*")
public class SignupController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody(required = true) SignupDTO signupRequest) {

        if (userService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("This email is already exist" + signupRequest.getEmail(),
                    HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO createdUser = userService.createUser(signupRequest);
        if (createdUser == null) {
            return new ResponseEntity<>("User is not created, try again later",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
}
