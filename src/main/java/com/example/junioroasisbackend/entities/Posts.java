package com.example.junioroasisbackend.entities;

import com.example.junioroasisbackend.dtos.PostDTO;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(name= "body", length = 600)
    private String body;

    private Date createdDate;

    //@ElementCollection(targetClass = String.class)
    //private List<String> tags;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // to exclude the User entity property from being serialized into JSON when the containing object is converted to JSON format.
    private User user;

    //TODO Mapping instead
    public PostDTO getPostDto() {
    PostDTO postDTO = new PostDTO();
    postDTO.setId(id); // get the id from entity, then set it to dto
    postDTO.setBody(body);
    postDTO.setTitle(title);
    //postDTO.setTags(tags);
    postDTO.setUserId(user.getId());
    postDTO.setUserName(user.getName());
    return postDTO;
    }

}
