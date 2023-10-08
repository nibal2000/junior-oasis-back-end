package com.example.junioroasisbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SignupDTO {
    private Long id;

    private String name;

    private String email;

    private String password;
}
