package com.example.junioroasisbackend.services.comments;

import com.example.junioroasisbackend.dtos.requests.CommentRequestDTO;
import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.entities.Comment;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.repositories.CommentRepository;
import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.services.user.UserService;
import com.example.junioroasisbackend.utils.Convertor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO) {
        return  CommentResponseDTO.mapToCommentDto(commentRepository.save(this.assignCommentRequestedToComment(commentRequestDTO, this.userService.getCurrentUser())));
    }




    @Override
    public Comment assignCommentRequestedToComment(CommentRequestDTO commentStoreDTO, User user) {
        Comment comment = new Comment();
        comment.setBody(commentStoreDTO.getBody());
        Post post = postRepository.findById(commentStoreDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        comment.setPost(post);
        comment.setUser(user);
        return comment;
    }

    @Override
    public Page<CommentResponseDTO> getAllComments(Integer pageNumber, Integer perPage, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, perPage, Sort.by(sortBy).descending());
        Page<Comment> page = commentRepository.findAll(paging);
        return new PageImpl<>(Convertor.CommentToCommentResponseDto(page.getContent()), paging, page.getTotalElements());
    }

    @Override
    public Comment getCommentById(Long commentId) throws Exception {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new Exception("Comment not found")
        );
    }

    @Override
    public boolean deleteCommentById(Long commentId) throws Exception {
        Comment comment = this.getCommentById(commentId);
        User user = this.userService.getCurrentUser();
        if (user.getId() == comment.getUser().getId()) {
            commentRepository.deleteById(commentId);
            return true ;
        }
        return false ;
    }

    @Override
    public Comment updateComment(Comment comment, CommentRequestDTO commentRequestDTO) {
        comment.setBody(commentRequestDTO.getBody());
        Post post = postRepository.findById(commentRequestDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @Override
    public Page<CommentResponseDTO> getAllCommentsByPost(Long postId, Integer pageNumber, Integer perPage, String sortBy) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Pageable paging = PageRequest.of(pageNumber, perPage, Sort.by(sortBy).descending());
        Page<Comment> page = commentRepository.findAllByPost(post , paging);
        return new PageImpl<>(Convertor.CommentToCommentResponseDto(page.getContent()), paging, page.getTotalElements());
    }

}
