package com.example.junioroasisbackend.dtos.requests;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequestDTO {

    @NotNull
    @Min(8)
    @Max(1024)
    private  String body;

    @NotNull
    private Long postId;

}
