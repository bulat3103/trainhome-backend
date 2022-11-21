package com.example.trainhome.userservice.repositories;

import com.example.trainhome.userservice.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query(value = "select * from training where id = :id", nativeQuery = true)
    Training getById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from training where id =: id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
}