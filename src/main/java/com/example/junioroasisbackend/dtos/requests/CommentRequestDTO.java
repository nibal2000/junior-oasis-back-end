package com.example.junioroasisbackend.dtos.requests;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CommentRequestDTO {

    @NotBlank
    @Size(max = 1024, min= 8)
    private  String body;

    @NotNull
    private Long postId;

}
