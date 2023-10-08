package com.example.junioroasisbackend.dtos.responses;

import com.example.junioroasisbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AuthResponseDTO {

    private String name;

    private String email;

    public AuthResponseDTO(User user){
        this.name = user.getName();
        this.email = user.getEmail();
    }
}