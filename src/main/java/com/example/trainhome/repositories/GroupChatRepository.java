package com.example.trainhome.repositories;


import com.example.trainhome.entities.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {

    @Query(value = "select * from group_chat where id = :id", nativeQuery = true)
    GroupChat findGroupChatById(@Param("id") Long id);

}
