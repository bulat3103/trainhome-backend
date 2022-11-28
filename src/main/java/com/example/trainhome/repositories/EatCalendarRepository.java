package com.example.trainhome.repositories;

import com.example.trainhome.entities.EatCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface EatCalendarRepository extends JpaRepository<EatCalendar, Long> {

    @Query(value = "select * from eat_calendar where id = :id", nativeQuery = true)
    EatCalendar getById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from eat_calendar where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update eat_calendar set date = :date, info = :info where id = :id", nativeQuery = true)
    void updateRecommendation(@Param("id") Long id, @Param("date") Date date, @Param("info") String info);

    @Query(value = "select * from eat_calendar where coach_id =: coachId and person_id =: personId and date =: date", nativeQuery = true)
    EatCalendar getByPersonIdAndCoachIdAndDate(@Param("coachId") Long coachId, @Param("personId") Long personId,
                                               @Param("date") Date date);

    @Query(value = "select * from eat_calendar where person_id =: personId and date =: date", nativeQuery = true)
    List<EatCalendar> getAllByPersonIdAndDate(@Param("personId") Long personId, @Param("date") Date date);
}
