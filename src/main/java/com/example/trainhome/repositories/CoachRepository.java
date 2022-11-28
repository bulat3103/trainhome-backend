package com.example.trainhome.repositories;

import com.example.trainhome.entities.Coach;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    @NonNull Coach getById(@NonNull Long personId);

    @Modifying
    @Transactional
    @Query(value = "update coach set info = :info, achievements = :achieve where person_id = :id", nativeQuery = true)
    void fillCoach(@Param("id") Long id, @Param("info") String info, @Param("achieve") String achieve);
}
