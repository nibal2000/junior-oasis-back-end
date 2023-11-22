package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.dtos.requests.PostRequestDTO;
import com.example.junioroasisbackend.dtos.responses.MediaResponseDTO;
import com.example.junioroasisbackend.dtos.responses.PostResponseDTO;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.services.comments.CommentService;
import com.example.junioroasisbackend.services.media.MediaService;
import com.example.junioroasisbackend.services.posts.PostService;
import com.example.junioroasisbackend.utils.Convertor;
import com.example.junioroasisbackend.utils.enums.Mediable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MediaService mediaService;


    @GetMapping("posts")
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "perPage", defaultValue = "5") int perPage
            , @RequestParam(defaultValue = "createdAt") String sortBy) {
        String[] attrs = {"id", "title", "body", "createdAt", "tags"};
        return ResponseEntity.ok(postService.getAllPosts(page, perPage, Arrays.asList(attrs).contains(sortBy) ? sortBy : "createdAt"));
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId,
                                         @RequestParam(name = "commentsPage", defaultValue = "0") int page,
                                         @RequestParam(name = "commentsPerPage", defaultValue = "5") int perPage,
                                         @RequestParam(defaultValue = "commentsCreatedAt") String sortBy) {
        try {
            return ResponseEntity.ok(postService.getPostWithComments(postId, page, perPage, sortBy));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("posts")
    public ResponseEntity<?> storePost(@ModelAttribute @Valid PostRequestDTO postDTO) throws Exception {
        PostResponseDTO postResponseDTO = postService.addPost(postDTO);
        if (postDTO.getImages() != null) {
            postResponseDTO.setImages(MediaResponseDTO.mapListToDTO(
                    mediaService.storeListMedia(Arrays.stream(postDTO.getImages())
                                    .collect(Collectors.toList()),
                            postResponseDTO.getId(), Mediable.POST)));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
    }


    @PatchMapping("posts/{postId}")
    public ResponseEntity<?> updatePost(@ModelAttribute @Valid PostRequestDTO postDTO, @PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);
            if (postDTO.getImages() != null) {
                mediaService.deleteAllMediaEntity(Mediable.POST, postId);
                mediaService.storeListMedia(Arrays.stream(postDTO.getImages()).collect(Collectors.toList()), postId, Mediable.POST);
            }
            return ResponseEntity.ok(PostResponseDTO.mapToDto(postService.updatePost(post, postDTO)));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            mediaService.deleteAllMediaEntity(Mediable.POST, postId);
            postService.deletePostById(postId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
