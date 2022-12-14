package com.example.trainhome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.trainhome.entities.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(value = "select * from person where email = :email", nativeQuery = true)
    Person findByEmail(@Param("email") String email);

    @Modifying
    @Query(value = "delete from person where id = :id", nativeQuery = true)
    Person deletePerson(@Param("id") Long id);

    @Query(value = "select * from person where id = :id", nativeQuery = true)
    Person findPersonById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update person set name = :name and " +
            "phone_number = :phoneNumber and birthday = :birthday where id = :id", nativeQuery = true)
    Person updatePerson(@Param("id") Long id, @Param("name") String name,
                        @Param("phoneNumber") String phoneNumber, @Param("birthday") Date  birthday);

    void deleteById(Long id);

    @Query(value = "select * from person where id = :id", nativeQuery = true)
    Person getById(@Param("id") Long id);

    @Query(value = "select * from get_all_person_in_group(:groupId)", nativeQuery = true)
    List<Person> getAllPersonsInGroup(@Param("groupId") Long groupId);
}