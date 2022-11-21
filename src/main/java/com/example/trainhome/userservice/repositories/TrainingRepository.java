package com.example.trainhome.userservice.repositories;

import com.example.trainhome.userservice.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
}
