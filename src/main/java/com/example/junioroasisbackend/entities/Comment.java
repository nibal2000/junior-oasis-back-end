package com.example.junioroasisbackend.entities;

import com.example.junioroasisbackend.entities.depentantEntities.CommentVote;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "comments")
public class Comment  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(name = "body", length = 512)
    private String body;

    @Column(name = "createdDate")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // one user can post many comments
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // one user can post many comments
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "entity_id", referencedColumnName = "id") // Assuming the foreign key is based on the "id" field
    @Where(clause = " entity_type = 'COMMENT' ")
    private List<Media> media;


    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "comment_id", referencedColumnName = "id") // Assuming the foreign key is based on the "id" field
    private List<CommentVote> votes;


}
