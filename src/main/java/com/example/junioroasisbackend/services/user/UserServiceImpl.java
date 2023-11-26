package com.example.junioroasisbackend.services.user;

import com.example.junioroasisbackend.dtos.requests.SignupRequestDTO;
import com.example.junioroasisbackend.dtos.responses.users.UserResponseDTO;
import com.example.junioroasisbackend.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.junioroasisbackend.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserResponseDTO createUser(SignupRequestDTO signupDTO) {
        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setName(signupDTO.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserResponseDTO createdUserDto = new UserResponseDTO();
        createdUserDto.setId(createdUser.getId());
        createdUserDto.setName(createdUser.getName());
        createdUserDto.setEmail(createdUser.getEmail());
        return createdUserDto;
    }

    @Override
    public boolean hasUserWithEmail(String username) {
        return userRepository.findFirstByEmail(username).isPresent();
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        return  optionalUser.orElseThrow();
    }

}
