package com.example.junioroasisbackend.services.posts;

import com.example.junioroasisbackend.dtos.requests.PostRequestDTO;
import com.example.junioroasisbackend.dtos.responses.PostResponseDTO;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.entities.User;
import org.springframework.data.domain.Page;

public interface PostService {
    PostResponseDTO addPost(PostRequestDTO postDTO) ;

    Page<PostResponseDTO> getAllPosts(Integer pageNumber , Integer perPage , String sortBy);

    Post getPostById(Long postId) throws Exception;
    void deletePostById(Long postId);

    Post updatePost(Post post , PostRequestDTO postRequestDTO );

    Post assignPostRequestedToPost(PostRequestDTO postRequestDTO , User user);

    PostResponseDTO getPostWithComments(Long postId , Integer pageNumber, Integer perPage, String sortBy) throws Exception;
}
