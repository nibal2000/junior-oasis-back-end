package com.example.junioroasisbackend.repositories.dependantRepositories;

import com.example.junioroasisbackend.entities.Comment;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.entities.depentantEntities.CommentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {

    @Modifying
    @Query("delete FROM CommentVote t WHERE t.comment = ?1 AND t.user = ?2")
    void deleteCommentVoteByCommentAndUser(Comment comment , User user);
}