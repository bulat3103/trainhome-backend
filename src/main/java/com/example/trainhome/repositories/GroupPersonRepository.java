package com.example.trainhome.repositories;

import com.example.trainhome.entities.GroupPerson;
import com.example.trainhome.entities.compositeKeys.GroupPersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPersonRepository extends JpaRepository<GroupPerson, GroupPersonId> {

    @Query(value = "select * from group_person where group_id =: id", nativeQuery = true)
    List<GroupPerson> getAllByGroupId(@Param("groupId") Long id);

    @Query(value = "select * from group_person where person_id =: id", nativeQuery = true)
    List<GroupPerson> getAllByPersonId(@Param("personId") Long id);
}
