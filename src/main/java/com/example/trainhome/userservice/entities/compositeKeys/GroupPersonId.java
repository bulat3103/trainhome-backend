package com.example.trainhome.userservice.entities.compositeKeys;

import com.example.trainhome.userservice.entities.Groups;
import com.example.trainhome.userservice.entities.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class GroupPersonId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Groups groupId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Person personId;

    public GroupPersonId(Groups groupId, Person personId) {
        this.groupId = groupId;
        this.personId = personId;
    }
}