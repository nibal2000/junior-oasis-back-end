package com.example.junioroasisbackend.dtos.responses;

import com.example.junioroasisbackend.dtos.responses.users.OwnerResponseDTO;
import com.example.junioroasisbackend.entities.Comment;
import lombok.*;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Data
public class CommentResponseDTO implements Serializable {

    private Long id;

    private String body;

    private String createdAt ;

    private Long postId;

    private OwnerResponseDTO Owner;



    public static CommentResponseDTO mapToCommentDto(Comment comment){
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setOwner(OwnerResponseDTO.mapToDto(comment.getUser()));
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setBody(comment.getBody());
        commentResponseDTO.setCreatedAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        commentResponseDTO.setPostId(comment.getPost().getId());// to see if i have to use postShowDto or let it like that
        return commentResponseDTO;
    }
}


