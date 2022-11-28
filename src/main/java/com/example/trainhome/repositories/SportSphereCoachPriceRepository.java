package com.example.trainhome.repositories;

import com.example.trainhome.entities.SportSphereCoachPrice;
import com.example.trainhome.entities.compositeKeys.SportSphereCoachPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportSphereCoachPriceRepository extends JpaRepository<SportSphereCoachPrice, SportSphereCoachPriceId> {

    @Query(value = "select * from sport_sphere_coach_price where sport_sphere_id = :id and price between :minPrice and :maxPrice", nativeQuery = true)
    List<SportSphereCoachPrice> getByNameAndPrice(@Param("id") Long id, @Param("minPrice") Integer minPrice,
                                            @Param("maxPrice") Integer maxPrice);
}
