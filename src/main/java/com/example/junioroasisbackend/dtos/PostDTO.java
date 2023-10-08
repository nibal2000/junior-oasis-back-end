package com.example.junioroasisbackend.dtos;

import lombok.Data;

@Data
public class PostDTO {

    private Long id;

    private String title;

    private String body;

    //private List<String> tags;

    private Long userId;

}
