package com.helphi.question.dao;

import java.time.Instant;
import java.util.Set;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.datastax.oss.driver.api.mapper.annotations.Update;
import com.helphi.question.api.Question;
import com.helphi.question.api.UserResponse;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions.questions_by_condition WHERE condition_id = :conditionId")
    PagingIterable<Question> getConditionQuestions(String conditionId);

    @Query("SELECT * FROM questions.user_responses_by_condition_user WHERE condition_id = :conditionId AND user_id = :userId")
    PagingIterable<UserResponse> getUsersResponsesForCondition(String userId,
        String conditionId);


    @Query("SELECT * FROM questions.questions_by_condition  WHERE condition_id = :conditionId AND question_id = :questionId")
    Question getById(String conditionId, Long questionId);

    @Insert
    void save(Question question);

    @Update
    void update(Question question);

    @Update
    void delete(Question question);


    // @Query("SELECT * FROM questions.question_answers WHERE answer_id= :answerId  ALLOW FILTERING")
    // QuestionAnswer getAnswer(long answerId);

    // @Query("SELECT * FROM questions.question_answers WHERE condition_id= :conditionId AND user_id= :userId ALLOW FILTERING")
    // PagingIterable<QuestionAnswer> getUsersAnswersForQuestion(long userId, long conditionId);

}
