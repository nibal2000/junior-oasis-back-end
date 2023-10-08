package com.example.junioroasisbackend.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthRequestDTO {
    @NotNull
    private String email;

    private String password;


}
