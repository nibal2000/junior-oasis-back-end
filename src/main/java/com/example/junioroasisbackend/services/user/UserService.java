package com.example.junioroasisbackend.services.user;

import com.example.junioroasisbackend.dtos.SignupDTO;
import com.example.junioroasisbackend.dtos.UserDTO;

public interface UserService {
    UserDTO createUser(SignupDTO signupDTO);

    boolean hasUserWithEmail(String email);
}
