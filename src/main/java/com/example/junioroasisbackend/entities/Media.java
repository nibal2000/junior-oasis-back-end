package com.example.junioroasisbackend.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data()
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;


    private String contentType;

    @Column(name = "entity_id")
    private Long entityID;

    private String entityType;

}
