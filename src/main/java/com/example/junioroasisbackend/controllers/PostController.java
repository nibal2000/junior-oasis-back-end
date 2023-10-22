package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.dtos.requests.PostRequestDTO;
import com.example.junioroasisbackend.dtos.responses.PostShowDTO;
import com.example.junioroasisbackend.services.posts.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("posts")
    public ResponseEntity<?> getAll(@RequestParam(name = "page" , defaultValue = "0" ) int page
            , @RequestParam(name = "perPage" , defaultValue = "5" ) int perPage
            ,@RequestParam(defaultValue = "createdAt")  String sortBy) {
        String[] attrs  = { "id" , "title" , "body" , "createdAt" , "tags" };
        return ResponseEntity.ok(postService.getAllPosts(page , perPage , Arrays.asList(attrs).contains(sortBy) ? sortBy : "createdAt"));
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(PostShowDTO.mapToDto(postService.getPostById(postId)));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("posts")
    public ResponseEntity<?> storePost(@RequestBody PostRequestDTO postDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.addPost(postDTO));
    }


    @PatchMapping ("posts/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostRequestDTO postDTO ,  @PathVariable Long postId) {
        try {

            return ResponseEntity.ok( PostShowDTO.mapToDto( postService.updatePost(postService.getPostById(postId), postDTO )));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePostById(postId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
