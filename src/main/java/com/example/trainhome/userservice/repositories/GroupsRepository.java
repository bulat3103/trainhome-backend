package com.example.trainhome.userservice.repositories;

import com.example.trainhome.userservice.entities.Groups;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    @Query(value = "select * from groups where coach_id =: id", nativeQuery = true)
    List<Groups> getByCoachId(@Param("id") Long id);

    Groups getById(@NonNull Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from groups where id =: id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
