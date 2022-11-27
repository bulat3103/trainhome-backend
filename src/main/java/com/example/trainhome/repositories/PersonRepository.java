package com.example.trainhome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.trainhome.entities.Person;

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
    @Query(value = "update person set image = :image and name = :name and " +
            "phone_number = :phone_number and birthday = :birthday where id = :id", nativeQuery = true)
    Person updatePerson(@Param("id") Long id, @Param("name") String name, @Param("image") String image,
                        @Param("phoneNumber") String phoneNumber, @Param("birthday") Date  birthday);
                        
    Person deletePerson(Person person);

    @Query(value = "select * from person where id =: id", nativeQuery = true)
    Person getById(@Param("id") Long id);

    @Query(value = "select get_all_person_in_group(:groupId) from person", nativeQuery = true)
    List<Person> getAllPersonsInGroup(@Param("groupId") Long groupId);
}