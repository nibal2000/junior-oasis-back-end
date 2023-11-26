package com.example.junioroasisbackend.dtos.responses.dependantDTOs;

import com.example.junioroasisbackend.entities.depentantEntities.PostLike;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PostLikeResponseDTO {
    private Long userId;
    private Long postId;

    public  static PostLikeResponseDTO mapToDTO(PostLike postLike){
        PostLikeResponseDTO postLikeResponseDTO = new PostLikeResponseDTO();
        postLikeResponseDTO.setUserId(postLike.getUser().getId());
        postLikeResponseDTO.setPostId(postLike.getPost().getId());
        return  postLikeResponseDTO;
    }


    public  static List<PostLikeResponseDTO> mapToListDTO(List<PostLike> postLikes){

        return postLikes.stream().map(PostLikeResponseDTO::mapToDTO).collect(Collectors.toList());
    }

}
