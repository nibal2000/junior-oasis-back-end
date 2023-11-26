package com.example.junioroasisbackend.services.comments.dependantServices;

import com.example.junioroasisbackend.dtos.requests.CommentRequestDTO;
import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.entities.Comment;
import com.example.junioroasisbackend.entities.User;
import org.springframework.data.domain.Page;

public interface CommentVoteService {

    void voteOnComment(Long commentId) throws Exception;

    void unVoteOnComment(Long commentId) throws Exception;
}
