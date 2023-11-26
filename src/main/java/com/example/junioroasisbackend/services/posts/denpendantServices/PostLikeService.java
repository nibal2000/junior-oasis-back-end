package com.example.junioroasisbackend.services.posts.denpendantServices;

public interface PostLikeService {
    void setLike (Long postId ) throws Exception;

    void  setUnlike (Long postId ) throws Exception;
}
