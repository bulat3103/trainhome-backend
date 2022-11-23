package com.example.trainhome.userservice.repositories;

import com.example.trainhome.userservice.entities.GroupPerson;
import com.example.trainhome.userservice.entities.compositeKeys.GroupPersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPersonRepository extends JpaRepository<GroupPerson, GroupPersonId> {
}
