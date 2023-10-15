package com.example.junioroasisbackend.dtos.responses;

import com.example.junioroasisbackend.dtos.PostDTO;
import lombok.*;

import java.util.List;

@Data
public class AllPostsResponseDto {

    private List<PostDTO> postDTOList;

    private Integer totalPages;

    private Integer pageNumber;

}
