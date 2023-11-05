package com.example.junioroasisbackend.services.comments;

import com.example.junioroasisbackend.dtos.requests.CommentRequestDTO;
import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.entities.Comment;
import com.example.junioroasisbackend.entities.User;
import org.springframework.data.domain.Page;

public interface CommentService {
    CommentResponseDTO addComment(CommentRequestDTO commentResponseDTO);

    Comment assignCommentRequestedToComment(CommentRequestDTO commentStoreDTO, User user);
    Page<CommentResponseDTO> getAllComments(Integer pageNumber, Integer perPage, String sortBy);

    Comment getCommentById(Long commentId) throws Exception;

    void deleteCommentById(Long commentId);

    Comment updateComment(Comment comment, CommentRequestDTO commentRequestDTO);
}