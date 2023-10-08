package com.example.junioroasisbackend.services.posts;

import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.repositories.UserRepository;
import com.example.junioroasisbackend.dtos.PostDTO;
import com.example.junioroasisbackend.entities.Posts;
import com.example.junioroasisbackend.entities.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @Override
    public PostDTO addPost(PostDTO postDTO) {
        // get the user from the DB (entity)
        Optional<User> optionalUser = userRepository.findById(postDTO.getId());
        if (optionalUser.isPresent()) {
            Posts post = new Posts();
            post.setTitle(postDTO.getTitle());
            post.setBody(postDTO.getBody());
            //post.setTags(postDTO.getTags());
            post.setCreatedDate(new Date());
            Posts createdPost = postRepository.save(post);
            PostDTO createdPostDto = new PostDTO();
            createdPostDto.setId(createdPost.getId());
            createdPostDto.setTitle(createdPost.getTitle());
            return createdPostDto; // if user is present, return createdPostDto
        }
        return null; // if user is not present return null
    }
}
