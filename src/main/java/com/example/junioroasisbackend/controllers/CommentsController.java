package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.dtos.requests.CommentRequestDTO;
import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.dtos.responses.MediaResponseDTO;
import com.example.junioroasisbackend.entities.Comment;
import com.example.junioroasisbackend.services.comments.CommentService;
import com.example.junioroasisbackend.services.media.MediaService;
import com.example.junioroasisbackend.utils.enums.Mediable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentsController {

    private final MediaService mediaService;


    private final CommentService commentService;




    @PostMapping(value = "comments")
    public ResponseEntity<?> storeComment(@ModelAttribute @Valid CommentRequestDTO commentRequestDTO ) throws Exception {

        CommentResponseDTO commentDTO = commentService.addComment(commentRequestDTO);
        if ( commentRequestDTO.getImage() != null ){

            commentDTO.setImage( MediaResponseDTO.mapToDTO(mediaService.storeMedia( commentRequestDTO.getImage()  , commentDTO.getId(),  Mediable.COMMENT)));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

    @GetMapping("comments")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "perPage", defaultValue = "5") int perPage,
                                    @RequestParam(defaultValue = "createdAt") String sortBy) {
        String[] attrs = {"id", "body", "createdAt"};
        return ResponseEntity.ok(commentService.getAllComments(page, perPage, Arrays.asList(attrs).contains(sortBy) ? sortBy : "createdAt"));
    }


    @GetMapping("comments/post/{postId}")
    public ResponseEntity<?> getAllByPostId(@PathVariable Long postId,
                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "perPage", defaultValue = "5") int perPage,
                                    @RequestParam(defaultValue = "createdAt") String sortBy) throws Exception {
        String[] attrs = {"id", "body", "createdAt"};
        return ResponseEntity.ok(commentService.getAllCommentsByPost(postId, page, perPage, Arrays.asList(attrs).contains(sortBy) ? sortBy : "createdAt"));
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
            mediaService.deleteAllMediaEntity(Mediable.COMMENT , commentId);
            commentService.deleteCommentById(commentId);
           // mediaService.
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("comments/{commentId}")
    public ResponseEntity<?> updateComment(@ModelAttribute @Valid CommentRequestDTO commentRequestDTO, @PathVariable Long commentId) {
        try {
            Comment comment =  commentService.getCommentById(commentId);
            if ( commentRequestDTO.getImage() != null ){
                mediaService.storeSingltonMedia( commentRequestDTO.getImage()  , comment.getId(),  Mediable.COMMENT);
            }
            return ResponseEntity.ok( CommentResponseDTO.mapToCommentDto(commentService.updateComment(comment, commentRequestDTO)));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
