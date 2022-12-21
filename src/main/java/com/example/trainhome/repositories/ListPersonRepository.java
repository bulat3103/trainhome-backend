package com.example.trainhome.repositories;

import com.example.trainhome.entities.ListPerson;
import com.example.trainhome.entities.compositeKeys.ListPersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListPersonRepository extends JpaRepository<ListPerson, ListPersonId> {

    @Query(value = "select chat_id from list_person where person_id = :person_id", nativeQuery = true)
    List<Long> getAllGroupChatByPersonId(@Param("person_id") Long personId);

    @Query(value = "select person_id from list_person where chat_id = :chat_id", nativeQuery = true)
    List<Long> getAllPersonIdInChat(@Param("chat_id") Long chatId);
}
