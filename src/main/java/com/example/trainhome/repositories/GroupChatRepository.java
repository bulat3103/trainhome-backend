package com.example.trainhome.repositories;


import com.example.trainhome.entities.GroupChat;
import com.example.trainhome.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {

    @Query(value = "select * from group_chat where id = :id", nativeQuery = true)
    GroupChat findGroupChatById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update group_chat set name = :name where id = :id", nativeQuery = true)
    void updateGroupChatName(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "delete from list_person where person_id = :person_id and chat_id = :chat_id", nativeQuery = true)
    void deletePersonInGroupChat(@Param("chat_id") Long ChatId, @Param("person_id") Long personId);

    @Query(value = "select chat_id from list_person where person_id = :person_id", nativeQuery = true)
    List<GroupChat> getAllGroupChatByPersonId(@Param("person_id") Long personId);

    @Query(value = "select * from get_all_person_in_group_chat(:id)", nativeQuery = true)
    List<Person> getAllPersonIdGroupChat(@Param("id") Long id);
}
