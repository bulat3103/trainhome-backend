package com.example.trainhome.entities.compositeKeys;

import com.example.trainhome.entities.GroupChat;
import com.example.trainhome.entities.Person;
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
public class ListPersonId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private GroupChat chatId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person personId;

    public ListPersonId(GroupChat chatId, Person personId) {
        this.chatId = chatId;
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListPersonId)) return false;
        ListPersonId that = (ListPersonId) o;
        return Objects.equals(getChatId(), that.getChatId()) && Objects.equals(getPersonId(), that.getPersonId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId(), getPersonId());
    }
}
