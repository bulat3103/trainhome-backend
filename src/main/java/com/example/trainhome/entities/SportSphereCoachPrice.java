package com.example.trainhome.entities;

import com.example.trainhome.entities.compositeKeys.SportSphereCoachPriceId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sport_sphere_coach_price")
@NoArgsConstructor
public class SportSphereCoachPrice {
    @EmbeddedId
    private SportSphereCoachPriceId id;

    @Column(name = "price", nullable = false)
    private Integer price;

    public SportSphereCoachPrice(SportSphereCoachPriceId id, Integer price) {
        this.id = id;
        this.price = price;
    }
}
