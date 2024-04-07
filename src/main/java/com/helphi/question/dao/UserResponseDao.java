package com.helphi.question.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import com.helphi.question.api.Question;
import com.helphi.question.api.UserResponse;

@Dao
public interface UserResponseDao {

    @Query("SELECT * FROM questions.user_responses_by_condition_user WHERE user_id = :userId AND condition_id = :conditionId AND response_id = :responseId")
    UserResponse getById(String userId, String conditionId, Long responseId);

    @Insert
    void save(UserResponse userResponse);

    @Update
    void update(UserResponse userResponse);

    @Update
    void delete(UserResponse userResponse);

    @Select(customWhereClause = "user_id = :userId AND condition_id = :conditionId AND response_id >= :cutoffId")
    PagingIterable<UserResponse> getUserResponses(String userId, String conditionId, long cutoffId);


    @Select(limit = "1", customWhereClause = "user_id = :userId AND condition_id = :conditionId")
    UserResponse getMostRecentResponse(String userId, String conditionId);


}
