package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.dtos.requests.SinglePostDTO;
import com.example.junioroasisbackend.dtos.responses.AllPostsResponseDto;
import com.example.junioroasisbackend.services.posts.PostService;
import com.example.junioroasisbackend.dtos.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PostsController {

    private final PostService postService;


    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createPostDto = postService.addPost(postDTO);
        if (createPostDto == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostDto);
    }

    @GetMapping("/posts/{pageNumber}")
    public ResponseEntity<AllPostsResponseDto> getAllPosts(@PathVariable int pageNumber) {
        AllPostsResponseDto allPostsResponseDto = postService.getAllPosts(pageNumber);
        return ResponseEntity.ok(allPostsResponseDto);
    }

    @GetMapping("post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        SinglePostDTO singlePostDTO = postService.getPostById(postId);
        if (singlePostDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(singlePostDTO);
    }
}
