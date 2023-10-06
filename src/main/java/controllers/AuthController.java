package controllers;

import dtos.AuthRequestDTO;
import dtos.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repositories.UserRepository;
import utils.JwtUtil;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    // config jwt for local storage
    public static final String TOKEN_PREFIX = "Bearer";

    public static final String HEADER_STRING = "Authorization";

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/auth")
    public AuthResponseDTO createAuthToken(@RequestBody AuthRequestDTO authRequest,
                                           HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Inncorrect Email or Password");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
            return null;
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // Configuration JWT in local storage
        // Create the object of the user to get the user from the repository
        // Optional<User> optionalUser =
        // userRepository.findFirstByEmail(userDetails.getUsername());

        // check if optional user is present
        // if (optionalUser.isPresent()) {
        // response.getWriter()
        // .write(new JSONObject().put("userId",
        // optionalUser.get().getId()).toString());
        // }

        // set the response to the header
        // response.addHeader("Access-Control-Expose-Headers", "Authorization");
        // response.setHeader("Access-Control-Allow-Headers", "Authorization,
        // X-PINGOTHER, X-Requested-with, Content-Type, Accept, X-Custom-header");
        // response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

        // no need for it any more
        return new AuthResponseDTO(jwt);
    }
}
