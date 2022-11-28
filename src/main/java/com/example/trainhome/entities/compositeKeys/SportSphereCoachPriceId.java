package com.example.trainhome.entities.compositeKeys;

import com.example.trainhome.entities.Coach;
import com.example.trainhome.entities.SportSphere;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class SportSphereCoachPriceId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_id")
    private Coach coachId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sport_sphere_id")
    private SportSphere sportSphereId;

    public SportSphereCoachPriceId(Coach coachId, SportSphere sportSphereId) {
        this.coachId = coachId;
        this.sportSphereId = sportSphereId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SportSphereCoachPriceId)) return false;
        SportSphereCoachPriceId that = (SportSphereCoachPriceId) o;
        return Objects.equals(getCoachId(), that.getCoachId()) && Objects.equals(getSportSphereId(), that.getSportSphereId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoachId(), getSportSphereId());
    }
}
