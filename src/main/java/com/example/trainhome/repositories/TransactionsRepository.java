package com.example.trainhome.repositories;

import com.example.trainhome.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Query(value = "select * from transactions where coach_id =: id", nativeQuery = true)
    List<Transactions> getAllByCoachId(@Param("id") Long id);

    @Query(value = "select sum(money) as total from transactions where coach_id =: id", nativeQuery = true)
    Integer getCoachMoney(@Param("id") Long id);
}
