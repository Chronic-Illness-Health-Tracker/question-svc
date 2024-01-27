package com.helphi.question.dao;

import java.time.Instant;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.helphi.question.api.Question;
import com.helphi.question.api.UserResponse;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions.questions_by_condition WHERE condition_id = :conditionId")
    PagingIterable<Question> getConditionQuestions(String conditionId);

    @Query("SELECT * FROM questions.user_responses_by_condition WHERE condition_id = :conditionId AND bucket = :bucket")
    PagingIterable<UserResponse> getUsersResponsesForCondition(String userId, int bucket, 
        String conditionId);

    @Query("SELECT * FROM questions.questions_by_condition WHERE condition_id = :conditionId AND question_id = :questionId")
    Question getQuestion(long questionId, String conditionId);
    
    @Query("INSERT INTO questions.questions_by_condition (question_id, condition_id, answer, created_at)"
        + "VALUES("
        + ":questionId,"
        + ":conditionId,"
        + "{answer_id: :answerId,"
        + "answer_text: :answer,"
        + "answer_value: :answerValue},"
        + ":timestamp"
        + ")")   
    void addQuestion(Long questionId, String conditionId, long answerId, String answer, int answerValue, Instant timestamp);


    // @Query("SELECT * FROM questions.question_answers WHERE answer_id= :answerId  ALLOW FILTERING")
    // QuestionAnswer getAnswer(long answerId);

    // @Query("SELECT * FROM questions.question_answers WHERE condition_id= :conditionId AND user_id= :userId ALLOW FILTERING")
    // PagingIterable<QuestionAnswer> getUsersAnswersForQuestion(long userId, long conditionId);

}
