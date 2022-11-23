package com.example.trainhome.userservice.entities;

import com.example.trainhome.userservice.entities.compositeKeys.GroupPersonId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "group_person")
@NoArgsConstructor
public class GroupPerson {
    @EmbeddedId
    private GroupPersonId id;

    public GroupPerson(GroupPersonId id) {
        this.id = id;
    }
}
