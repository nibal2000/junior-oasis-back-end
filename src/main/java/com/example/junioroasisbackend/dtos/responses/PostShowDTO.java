package com.example.junioroasisbackend.dtos.responses;

import com.example.junioroasisbackend.dtos.responses.users.OwnerResponseDTO;
import com.example.junioroasisbackend.entities.Post;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class PostShowDTO {

    private Long id;

    private String title;

    private String body;

    private String createdAt ;

    private List<String> tags;

    private OwnerResponseDTO owner;




    public static PostShowDTO mapToDto(Post post) {
        PostShowDTO postShowDTO = new PostShowDTO();
        postShowDTO.setOwner(OwnerResponseDTO.mapToDto(post.getUser()));
        postShowDTO.setId(post.getId());
        postShowDTO.setCreatedAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        postShowDTO.setTitle(post.getTitle());
        postShowDTO.setBody(post.getBody());
        postShowDTO.setTags(post.getTags());
        return postShowDTO;
    }
}
