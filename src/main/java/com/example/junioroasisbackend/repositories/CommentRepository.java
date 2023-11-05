package com.example.junioroasisbackend.repositories;

import com.example.junioroasisbackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
