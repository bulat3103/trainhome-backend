package com.example.trainhome.repositories;

import com.example.trainhome.entities.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Query(value = "select from coach where person_id = :personId", nativeQuery = true)
    Coach getByPersonId(@Param("personId") Long personId);
    @Query(value = "update coach set info = :info and achievements = :achieve where person_id = :id", nativeQuery = true)
    void fillCoach(@Param("id") Long id, @Param("info") String info, @Param("achieve") String achieve);
}
