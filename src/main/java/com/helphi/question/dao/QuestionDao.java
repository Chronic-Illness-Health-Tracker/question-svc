package com.helphi.question.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;

//@Dao
public interface QuestionDao {

    // @Query("SELECT * FROM questions.question_answers WHERE answer_id= :answerId  ALLOW FILTERING")
    // QuestionAnswer getAnswer(long answerId);

    // @Query("SELECT * FROM questions.question_answers WHERE condition_id= :conditionId AND user_id= :userId ALLOW FILTERING")
    // PagingIterable<QuestionAnswer> getUsersAnswersForQuestion(long userId, long conditionId);

}
