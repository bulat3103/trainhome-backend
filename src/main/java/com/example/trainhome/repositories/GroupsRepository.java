package com.example.trainhome.repositories;

import com.example.trainhome.entities.Groups;
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

    @Query(value = "select * from groups where id =: id", nativeQuery = true)
    Groups getById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from groups where id =: id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update groups set name =: name, maxCount =: maxCount, trainsLeft =: trainsLeft where id =: id", nativeQuery = true)
    int updateGroup(@Param("id") Long id, @Param("name") String name, @Param("maxCount") Integer maxCount, @Param("trainsLeft") Integer trainsLeft);
}
