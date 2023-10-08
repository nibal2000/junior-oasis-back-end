package dtos;

import lombok.Getter;
import lombok.Setter;

public class AuthResponseDTO {
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public AuthResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String toString() {
        return "AuthResponseDTO{" +
                "jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
