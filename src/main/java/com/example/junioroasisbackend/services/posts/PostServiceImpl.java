package com.example.junioroasisbackend.services.posts;

import com.example.junioroasisbackend.dtos.requests.PostRequestDTO;
import com.example.junioroasisbackend.dtos.responses.PostShowDTO;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.repositories.UserRepository;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.utils.Convertor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PostServiceImpl implements PostService {


    private final UserRepository userRepository;

    private final PostRepository postRepository;


    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public PostShowDTO addPost(PostRequestDTO postDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        return optionalUser.map(user -> PostShowDTO.mapToDto(postRepository.save(this.assignPostRequestedToPost(postDTO, user)))).orElse(null);

    }

    @Override
    public Page<PostShowDTO> getAllPosts(Integer pageNumber, Integer perPage, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, perPage, Sort.by(sortBy).descending());
        Page<Post> page = postRepository.findAll(paging);
        return new PageImpl<>(Convertor.PostToPostShowDTO(page.getContent()), paging, page.getTotalElements());
    }


    @Override
    public Post getPostById(Long postId) throws Exception {
        // PostShowDTO
        return postRepository.findById(postId).orElseThrow(
                () -> new Exception("Post not found")
        );
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Post updatePost(Post post, PostRequestDTO postRequestDTO) {
        post.setTitle(postRequestDTO.getTitle());
        post.setBody(postRequestDTO.getBody());
        post.setTags(postRequestDTO.getTags());
        return postRepository.save(post);
    }

    @Override
    public Post assignPostRequestedToPost(PostRequestDTO postStoreDTO, User user) {
        Post post = new Post();
        post.setTitle(postStoreDTO.getTitle());
        post.setBody(postStoreDTO.getBody());
        post.setTags(postStoreDTO.getTags());
        post.setUser(user);
        return post;
    }
}