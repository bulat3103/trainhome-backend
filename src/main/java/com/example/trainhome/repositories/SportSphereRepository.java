package com.example.trainhome.repositories;

import com.example.trainhome.entities.SportSphere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportSphereRepository extends JpaRepository<SportSphere, Long> {
    SportSphere getByName(String name);
}
