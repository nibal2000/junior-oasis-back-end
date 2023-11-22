package com.example.junioroasisbackend.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.DbName;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Data
@Table(name = "posts")
public class Post  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Lob
    @Column(name= "body", length = 600)
    private String body;

    @Column(name = "createdDate")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // to exclude the User entity property from being serialized into JSON when the containing object is converted to JSON format.
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entity_id", referencedColumnName = "id") // Assuming the foreign key is based on the "id" field
    @Where(clause = " entity_type = 'POST' ")
    private List<Media> media;

}