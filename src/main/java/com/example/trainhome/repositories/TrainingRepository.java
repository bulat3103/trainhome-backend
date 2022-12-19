package com.example.trainhome.repositories;

import com.example.trainhome.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query(value = "select * from training where id = :id", nativeQuery = true)
    Training getById(@Param("id") Long id);

    @Query(value = "select * from get_person_trainings(:id)", nativeQuery = true)
    List<Training> getAllByPersonId(@Param("id") Long id);

    @Query(value = "select * from training where coach_id = :id", nativeQuery = true)
    List<Training> getAllByCoachId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from training where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update training set training_date = :trainingDate, link = :link where id = :id", nativeQuery = true)
    int updateTraining(@Param("id") Long id, @Param("trainingDate") Date trainingDate, @Param("link") String link);
}
