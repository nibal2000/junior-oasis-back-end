package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.services.posts.PostService;
import com.example.junioroasisbackend.dtos.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/post")
public class PostsController {

    private final PostService postService;


    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createPostDto = postService.addPost(postDTO);
        if (createPostDto == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostDto);
    }
}