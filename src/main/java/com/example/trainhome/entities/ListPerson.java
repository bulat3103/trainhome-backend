package com.example.trainhome.entities;

import com.example.trainhome.entities.compositeKeys.ListPersonId;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "list_person")
public class ListPerson {
    @EmbeddedId
    private ListPersonId id;
}
