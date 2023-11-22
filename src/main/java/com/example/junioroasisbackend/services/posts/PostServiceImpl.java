package com.example.junioroasisbackend.services.posts;

import com.example.junioroasisbackend.dtos.requests.PostRequestDTO;
import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.dtos.responses.PostResponseDTO;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.repositories.UserRepository;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.services.comments.CommentService;
import com.example.junioroasisbackend.utils.Convertor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final UserRepository userRepository;

    private final PostRepository postRepository;


    private final CommentService commentService;


    @Override
    public PostResponseDTO addPost(PostRequestDTO postDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        return optionalUser.map(user -> PostResponseDTO.mapToDto(postRepository
                        .save(this.assignPostRequestedToPost(postDTO, user))))
                .orElse(null);
    }

    @Override
    public Page<PostResponseDTO> getAllPosts(Integer pageNumber, Integer perPage, String sortBy) {
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

    @Override
    public PostResponseDTO getPostWithComments(Long postId, Integer pageNumber, Integer perPage, String sortBy) throws Exception {

        Post post = this.getPostById(postId);
        String[] attrs = {"id", "body", "createdAt"};
        Page<CommentResponseDTO> pageObj = commentService.getAllCommentsByPost(postId, pageNumber, perPage, Arrays.asList(attrs).contains(sortBy) ? sortBy : "createdAt");
        PostResponseDTO postResponseDTO = PostResponseDTO.mapToDto(post);
        postResponseDTO.setComments(pageObj);
        return postResponseDTO;
    }
}