package services.jwt;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // UserDetailsService is a default method from spring boot
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // get user from DB
        Optional<User> optionalUser = userRepository.findFirstByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User is not found");
        }
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(),
                optionalUser.get().getPassword(), new ArrayList<>());
    }
}
