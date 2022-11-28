package com.example.trainhome.repositories;

import com.example.trainhome.entities.ListMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListMessagesRepository extends JpaRepository<ListMessage, Long> {

    @Query(value = "select * from get_all_messages_in_group_chat(:id)", nativeQuery = true)
    List<ListMessage> getListMessageByChatId(@Param("id") Long id);
}
