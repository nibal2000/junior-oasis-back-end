package com.example.junioroasisbackend.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SignupRequestDTO {
    private Long id;

    private String name;

    private String email;

    private String password;
}
