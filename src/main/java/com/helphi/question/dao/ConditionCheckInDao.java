package com.helphi.question.dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import com.helphi.question.api.ConditionCheckIn;

@Dao
public interface ConditionCheckInDao {

    @Query("SELECT * FROM questions.condition_check_in WHERE condition_id = :conditionId")
    ConditionCheckIn getById(String conditionId);

    @Insert
    void save(ConditionCheckIn checkin);

    @Update
    void update(ConditionCheckIn checkin);

    @Update
    void delete(ConditionCheckIn checkin);
}
