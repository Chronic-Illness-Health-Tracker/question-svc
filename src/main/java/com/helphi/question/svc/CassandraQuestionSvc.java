package com.helphi.question.svc;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.helphi.question.api.grpc.RequestReply;
import com.helphi.question.api.grpc.Timescale;
import com.helphi.question.api.Question;
import com.helphi.question.api.UserResponse;
import com.helphi.question.dao.QuestionDao;

import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for question related business logic.*/
@Service
@RequiredArgsConstructor
public class CassandraQuestionSvc implements IquestionService {

    @Autowired
    private final QuestionDao questionDao;

    public Question getQuestion(long questionId, String conditonId) {
        return questionDao.getQuestion(questionId, conditonId);
    }

    public List<Question> getConditionQuestions(String conditionId) {
        PagingIterable<Question> dbResult = questionDao.getConditionQuestions(conditionId);
        return dbResult.all();
    }

    public UserResponse getUserResponse(String userId, String conditonId) {
        return null;
    }

    public List<UserResponse> getUserResponses(String userId, Timescale timescale) {
        return null;
    }

    public List<UserResponse> getUsersResponsesForCondition(String userId, String conditionId, 
        Timescale timescale) {
        return null;
    }

    public Question addQuestion(Question question) {

        question.setQuestionId(123456L);
        question.getAnswer().setAnswerId(82641L);

        questionDao.addQuestion(question.getQuestionId(), question.getConditionId(), 
            question.getAnswer().getAnswerId(), question.getAnswer().getAnswerText(), 
            question.getAnswer().getAnswerValue(), Instant.now());

        return this.getQuestion(question.getQuestionId(), question.getConditionId());
    }

    public Question updateQuestion(long questionId, Question question) {
        return null;
    }

    public RequestReply deleteQuestion(long questionId) {
        return null;
    }

    public RequestReply deleteAllQuestion(String conditionId) {
        return null;
    }

    public UserResponse addUserResponse(UserResponse response) {
        return null;     
    }

    public UserResponse updateUserResponse(UserResponse response) {
        return null;   
    }

    public RequestReply deleteUserResponse(long responseId) {
        return null;
    }
    
    public RequestReply deleteUserResponsesForCondition(String conditionId) {
        return null;
    }

    public RequestReply deleteAllUserResponses(String userId) { 
        return null;
    }
}
