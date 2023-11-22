package com.example.junioroasisbackend.dtos.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
public class CommentRequestDTO {


    private MultipartFile image;

    @NotBlank
    @Size(max = 1024, min= 8)
    private  String body;

    @NotNull
    private Long postId;



}
