package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.dtos.requests.CommentRequestDTO;
import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.services.comments.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CommentsController {

    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("comments")
    public ResponseEntity<?> storeComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(commentRequestDTO));
    }

    @GetMapping("comments")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "perPage", defaultValue = "5") int perPage,
                                    @RequestParam(defaultValue = "createdAt") String sortBy) {
        String[] attrs = {"id", "body", "createdAt"};
        return ResponseEntity.ok(commentService.getAllComments(page, perPage, Arrays.asList(attrs).contains(sortBy) ? sortBy : "createdAt"));
    }

    @GetMapping("comments/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId) {
        try {
            return ResponseEntity.ok(CommentResponseDTO.mapToCommentDto(commentService.getCommentById(commentId)));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteCommentById(commentId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("comments/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequestDTO commentRequestDTO, @PathVariable Long commentId) {
        try {
            return ResponseEntity.ok( CommentResponseDTO.mapToCommentDto(commentService.updateComment(commentService.getCommentById(commentId), commentRequestDTO)));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
