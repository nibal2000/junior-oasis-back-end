package com.example.junioroasisbackend.services.posts;

import com.example.junioroasisbackend.dtos.requests.PostRequestDTO;
import com.example.junioroasisbackend.dtos.responses.CommentResponseDTO;
import com.example.junioroasisbackend.dtos.responses.PostResponseDTO;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.services.comments.CommentService;
import com.example.junioroasisbackend.services.user.UserService;
import com.example.junioroasisbackend.utils.Convertor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final UserService userService;

    private final PostRepository postRepository;


    private final CommentService commentService;



    @Override
    public PostResponseDTO addPost(PostRequestDTO postDTO) {
        User user = userService.getCurrentUser();
       return  PostResponseDTO.mapToDto(postRepository
                .save(this.assignPostRequestedToPost(postDTO, user)));
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
    public boolean deletePostById(Long postId) throws Exception {

        Post post  = this.getPostById(postId);
        User user = this.userService.getCurrentUser();
        //Dans cette ligne, Objects.equals() est utilisé pour comparer les ID de l'utilisateur actuel (user.getId()) et de l'utilisateur associé au post (post.getUser().getId()).
        //Objects.equals() est utilisé pour comparer les ID des utilisateurs de manière sûre, prenant en compte les cas où l'un ou l'autre ID pourrait être nul sans provoquer une exception NullPointerException. Cela permet de vérifier l'égalité des ID sans risque d'erreur due à des références nulles.
        if (Objects.equals(user.getId(), post.getUser().getId())){
            postRepository.deleteById(postId);
            return true ;
        }
        return false ;
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
        Page<CommentResponseDTO> pageObj = commentService.getAllCommentsByPost(
                postId,
                pageNumber,
                perPage,
                Arrays.asList(attrs) //Arrays.asList(attrs) prend le tableau attrs (qui contient des chaînes de caractères) et le convertit en une liste (List<String>).
                        .contains(sortBy) ? sortBy : "createdAt"); // Vérifie si sortBy est un attribut valide, sinon utilise "createdAt"
        PostResponseDTO postResponseDTO = PostResponseDTO.mapToDto(post);
        postResponseDTO.setComments(pageObj);
        return postResponseDTO;
    }


}