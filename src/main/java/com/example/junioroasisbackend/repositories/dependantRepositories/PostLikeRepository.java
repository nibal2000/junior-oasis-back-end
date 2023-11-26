package com.example.junioroasisbackend.repositories.dependantRepositories;

import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.entities.depentantEntities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Modifying
    @Query("delete FROM PostLike t WHERE t.post = ?1 AND t.user = ?2")
    void deletePostLikeByPostAndUser(Post post  , User user );
}
