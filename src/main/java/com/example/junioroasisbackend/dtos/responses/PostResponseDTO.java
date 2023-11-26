package com.example.junioroasisbackend.dtos.responses;

import com.example.junioroasisbackend.dtos.responses.dependantDTOs.PostLikeResponseDTO;
import com.example.junioroasisbackend.dtos.responses.users.OwnerResponseDTO;
import com.example.junioroasisbackend.entities.Post;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PostResponseDTO implements Serializable {

    private Long id;

    private String title;

    private String body;

    private String createdAt ;

    private List<String> tags;

    private OwnerResponseDTO owner;

    private List<MediaResponseDTO> images;

    private Page<CommentResponseDTO> comments;

    private List<PostLikeResponseDTO> likes ;
    public static PostResponseDTO mapToDto(Post post) {
        PostResponseDTO postShowDTO = new PostResponseDTO();
        postShowDTO.setOwner(OwnerResponseDTO.mapToDto(post.getUser()));
        postShowDTO.setId(post.getId());
        postShowDTO.setCreatedAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        postShowDTO.setTitle(post.getTitle());
        postShowDTO.setBody(post.getBody());
        postShowDTO.setTags(post.getTags());
        postShowDTO.setImages(MediaResponseDTO.mapListToDTO(post.getMedia()));
        if (post.getLikes() != null ) {
            postShowDTO.setLikes(PostLikeResponseDTO.mapToListDTO(post.getLikes())); // get likes return null , verify if not null , send the list
        }
        return postShowDTO;
    }
}
