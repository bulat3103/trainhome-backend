package com.example.trainhome.repositories;

import com.example.trainhome.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "select * from session where token = :token", nativeQuery = true)
    Session getByToken(@Param("token") String token);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update session set expired = true where id = :id", nativeQuery = true)
    void setExpirationById(@Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update session set expired = true where person_id = :id",
            nativeQuery = true)
    void setExpirationByPerson(@Param("id") long id);
}
