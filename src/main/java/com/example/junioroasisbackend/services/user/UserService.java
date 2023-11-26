package com.example.junioroasisbackend.services.user;

import com.example.junioroasisbackend.dtos.requests.SignupRequestDTO;
import com.example.junioroasisbackend.dtos.responses.users.UserResponseDTO;
import com.example.junioroasisbackend.entities.User;

public interface UserService {
    UserResponseDTO createUser(SignupRequestDTO signupDTO);

    boolean hasUserWithEmail(String email);

    User getCurrentUser();
}
