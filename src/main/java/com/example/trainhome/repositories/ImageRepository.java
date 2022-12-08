package com.example.trainhome.repositories;


import com.example.trainhome.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select * from image where id = :id", nativeQuery = true)
    Image findImageById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update image set hex = :hex where id = :id", nativeQuery = true)
    void updateById(@Param("id") Long id, @Param("hex") String hex);
}
