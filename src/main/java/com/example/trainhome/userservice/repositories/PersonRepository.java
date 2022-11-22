package com.example.trainhome.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.trainhome.userservice.entities.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);

    Person deletePerson(Person person);

    @Query(value = "select * from person where id =: id", nativeQuery = true)
    Person getById(@Param("id") Long id);
}