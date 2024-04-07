package com.helphi.question.svc;

import com.helphi.question.api.*;
import com.helphi.question.api.grpc.ListPatientStatusRequest;
import com.helphi.question.api.grpc.RequestReply;
import com.helphi.question.api.grpc.Timescale;

import java.util.List;

public interface IquestionService {

    public Question getQuestion(long questionId, String conditionId);

    public List<Question> getConditionQuestions(String conditionId);

    public UserResponse getUserResponse(String userId, String conditonId);

    public List<UserResponse> getUserResponses(String userId, Timescale timescale);

    public List<UserResponse> getUsersResponsesForCondition(String userId, String conditionId, 
        Timescale timescale);

    public Question addQuestion(Question question);

    public Question updateQuestion(long questionId, Question question);

    public RequestReply deleteQuestion(long questionId);

    public RequestReply deleteAllQuestion(String conditionId);

    public UserResponse addUserResponse(UserResponse response);

    public UserResponse updateUserResponse(UserResponse response);

    public RequestReply deleteUserResponse(long responseId);

    public RequestReply deleteUserResponsesForCondition(String conditionId);

    public RequestReply deleteAllUserResponses(String userId);

    public List<PatientStatus> getRecentPatientStatuses(ListPatientStatusRequest request);

    public void addCheckIn(ConditionCheckIn checkIn);
    public void updateCheckIn(ConditionCheckIn checkIn);

    public PatientStatus getCurrentStatus(String conditionId, String patientId);
    
}
