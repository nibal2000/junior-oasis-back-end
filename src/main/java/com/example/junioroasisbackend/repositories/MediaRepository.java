package com.example.junioroasisbackend.repositories;

import com.example.junioroasisbackend.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT t FROM Media t WHERE t.entityType = ?1 AND t.entityID = ?2")
    Collection<Media> findByTypeAndID(String entityType, Long entityID);

    @Query("delete FROM Media t WHERE t.entityType = ?1 AND t.entityID = ?2")
    void deleteByTypeAndID(String entityType, Long entityID);

}
