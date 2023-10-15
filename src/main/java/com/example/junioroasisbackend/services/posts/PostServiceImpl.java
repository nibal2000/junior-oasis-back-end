package com.example.junioroasisbackend.services.posts;

import com.example.junioroasisbackend.dtos.requests.SinglePostDTO;
import com.example.junioroasisbackend.dtos.responses.AllPostsResponseDto;
import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.repositories.UserRepository;
import com.example.junioroasisbackend.dtos.PostDTO;
import com.example.junioroasisbackend.entities.Posts;
import com.example.junioroasisbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    public static final int SEARCH_RESULT_PER_PAGE = 5;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    //TODO mapping
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

    @Override
    public AllPostsResponseDto getAllPosts(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE); // pageNumber(fromURl), pageSize(how many results i want to show)
        Page<Posts> postsPage = postRepository.findAll(paging); //getAllPosts from repository and save each page(with 5 elements) of posts into postPage obj
        AllPostsResponseDto allPostsResponseDto = new AllPostsResponseDto(); // create obj for all postsresponseDto
        // loop to set entity to dto
        allPostsResponseDto.setPostDTOList(postsPage.getContent().stream().map(Posts::getPostDto).collect(Collectors.toList()));
        allPostsResponseDto.setPageNumber(postsPage.getPageable().getPageNumber());
        allPostsResponseDto.setTotalPages(postsPage.getTotalPages());
        return allPostsResponseDto;
    }

    @Override
    public SinglePostDTO getPostById(Long postId) {
        Optional<Posts> optionalPost = postRepository.findById(postId);
        SinglePostDTO singlePostDTO = new SinglePostDTO();
        optionalPost.ifPresent(post -> singlePostDTO.setPostDTO(post.getPostDto())); // get post from entity and set postDto
        return singlePostDTO;
    }
}
