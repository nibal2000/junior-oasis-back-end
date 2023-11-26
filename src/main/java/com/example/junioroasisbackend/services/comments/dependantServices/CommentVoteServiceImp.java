package com.example.junioroasisbackend.services.comments.dependantServices;

import com.example.junioroasisbackend.entities.Comment;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.entities.depentantEntities.CommentVote;
import com.example.junioroasisbackend.repositories.dependantRepositories.CommentVoteRepository;
import com.example.junioroasisbackend.services.comments.CommentService;
import com.example.junioroasisbackend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentVoteServiceImp implements  CommentVoteService{



    private final CommentVoteRepository commentVoteRepository;
    private final UserService userService;
    private final CommentService commentService;
    @Override
    public void voteOnComment(Long commentId) throws Exception {
        Comment comment = this.commentService.getCommentById(commentId);
        User user = this.userService.getCurrentUser();
        this.commentVoteRepository.deleteCommentVoteByCommentAndUser(comment , user );
        CommentVote commentVote = new CommentVote();
        commentVote.setComment(comment);
        commentVote.setUser(user);
        this.commentVoteRepository.save(commentVote);
    }

    @Override
    public void unVoteOnComment(Long commentId) throws Exception {
        User user = this.userService.getCurrentUser();
        this.commentVoteRepository.deleteCommentVoteByCommentAndUser(this.commentService.getCommentById(commentId),  user );
    }
}
