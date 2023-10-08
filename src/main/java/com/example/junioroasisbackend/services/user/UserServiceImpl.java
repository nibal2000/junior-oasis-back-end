package com.example.junioroasisbackend.services.user;

import com.example.junioroasisbackend.dtos.SignupDTO;
import com.example.junioroasisbackend.dtos.UserDTO;
import com.example.junioroasisbackend.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.junioroasisbackend.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setName(signupDTO.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO createdUserDto = new UserDTO();
        createdUserDto.setId(createdUser.getId());
        return createdUserDto;
    }

    @Override
    public boolean hasUserWithEmail(String username) {
        return userRepository.findFirstByEmail(username).isPresent();
    }

}
