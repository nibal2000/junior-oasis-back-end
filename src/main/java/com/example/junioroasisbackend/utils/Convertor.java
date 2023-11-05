package com.example.junioroasisbackend.utils;

import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.dtos.responses.PostShowDTO;
import com.example.junioroasisbackend.entities.Comment;
import com.example.junioroasisbackend.entities.Post;

import java.util.List;
import java.util.stream.Collectors;

public class Convertor {

    public  static List<PostShowDTO> PostToPostShowDTO(List<Post> l ){
       return  l.stream().map(PostShowDTO::mapToDto).collect(Collectors.toList());
    }

    public  static List<CommentResponseDTO> CommentToCommentResponseDto(List<Comment> l ){
        return l.stream().map(CommentResponseDTO::mapToCommentDto).collect(Collectors.toList());
    }
}
