package com.example.trainhome.entities.compositeKeys;

import com.example.trainhome.entities.GroupChat;
import com.example.trainhome.entities.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ListPersonId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private GroupChat chatId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Person personId;
}
