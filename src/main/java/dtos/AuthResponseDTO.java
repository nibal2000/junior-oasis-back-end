package dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private String jwtToken;

    public AuthResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
