package com.example.trainhome.repositories;

import com.example.trainhome.entities.GroupPerson;
import com.example.trainhome.entities.compositeKeys.GroupPersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPersonRepository extends JpaRepository<GroupPerson, GroupPersonId> {
}
