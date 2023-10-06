package services.user;

import dtos.SignupDTO;
import dtos.UserDTO;

public interface UserService {
    UserDTO createUser(SignupDTO signupDTO);

    boolean hasUserWithEmail(String email);
}
