package com.example.trainhome.repositories;

import com.example.trainhome.entities.SportSphereCoachPrice;
import com.example.trainhome.entities.compositeKeys.SportSphereCoachPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportSphereCoachPriceRepository extends JpaRepository<SportSphereCoachPrice, SportSphereCoachPriceId> {
}
