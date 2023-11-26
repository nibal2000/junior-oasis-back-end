package com.example.junioroasisbackend.services.posts.denpendantServices;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.entities.depentantEntities.PostLike;
import com.example.junioroasisbackend.repositories.dependantRepositories.PostLikeRepository;
import com.example.junioroasisbackend.services.posts.PostService;
import com.example.junioroasisbackend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImp implements  PostLikeService{

    private final PostLikeRepository postLikeRepository;
    private final UserService userService;
    private final PostService postService;
    @Override
    public void setLike(Long postId) throws Exception {
        Post post  = this.postService.getPostById(postId);
        User user = this.userService.getCurrentUser();
        this.postLikeRepository.deletePostLikeByPostAndUser(post, user);// delete old like
        PostLike postLike = new PostLike();
        postLike.setPost(post);// set the new one
        postLike.setUser(user);
        this.postLikeRepository.save(postLike);
    }

    @Override
    public void setUnlike(Long postId) throws Exception {
        User user = this.userService.getCurrentUser();
        Post post  = this.postService.getPostById(postId);
        this.postLikeRepository.deletePostLikeByPostAndUser(post , user);
    }
}
