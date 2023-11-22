package com.example.junioroasisbackend.dtos.responses;

import com.example.junioroasisbackend.dtos.responses.users.OwnerResponseDTO;
import com.example.junioroasisbackend.entities.Comment;
import lombok.*;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Data
public class CommentResponseDTO implements Serializable {

    private Long id;

    private String body;

    private String createdAt ;

    private Long postId;

    private OwnerResponseDTO Owner;


    private MediaResponseDTO image;


    public static CommentResponseDTO mapToCommentDto(Comment comment){
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setOwner(OwnerResponseDTO.mapToDto(comment.getUser()));
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setBody(comment.getBody());
        commentResponseDTO.setCreatedAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        commentResponseDTO.setPostId(comment.getPost().getId());// to see if i have to use postShowDto or let it like that
        if(comment.getMedia() != null &&  !comment.getMedia().isEmpty()){
            commentResponseDTO.setImage(MediaResponseDTO.mapToDTO(comment.getMedia().get(0) ));
        }

        return commentResponseDTO;
    }
}


