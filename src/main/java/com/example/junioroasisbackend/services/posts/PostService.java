package com.example.junioroasisbackend.services.posts;

import com.example.junioroasisbackend.dtos.PostDTO;
import com.example.junioroasisbackend.dtos.requests.SinglePostDTO;
import com.example.junioroasisbackend.dtos.responses.AllPostsResponseDto;

public interface PostService {
    PostDTO addPost(PostDTO postDTO);

    AllPostsResponseDto getAllPosts(int pageNumber);

    SinglePostDTO getPostById(Long postId);
}
