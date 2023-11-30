package com.helphi.question.svc;

import com.helphi.api.Question;
import com.helphi.api.UserResponse;
import com.helphi.api.grpc.RequestReply;
import com.helphi.api.grpc.Timescale;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service for question related business logic.*/
@Service
@RequiredArgsConstructor
public class QuestionSvc {

    // @Autowired
    // private final QuestionDao questionDao;

    public Question getQuestion(long questionId) {
        return null;
    }

    public List<Question> getConditionQuestions(String conditionId) {
        return null;
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
        return null;
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
