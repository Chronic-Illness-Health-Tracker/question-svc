package com.helphi.question.svc;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.helphi.question.api.*;
import com.helphi.question.api.grpc.ListPatientStatusRequest;
import com.helphi.question.api.grpc.RequestReply;
import com.helphi.question.api.grpc.Timescale;
import com.helphi.question.dao.ConditionCheckInDao;
import com.helphi.question.dao.QuestionDao;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.helphi.question.dao.UserResponseDao;
import com.helphi.question.svc.id.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for question related business logic.*/
@Service
@RequiredArgsConstructor
public class CassandraQuestionSvc implements IquestionService {

    @Autowired
    private final QuestionDao questionDao;
    @Autowired
    private final UserResponseDao userResponseDao;
    @Autowired
    private final ConditionCheckInDao conditionCheckInDao;

    @Autowired
    private final Snowflake snowflake;

    public Question getQuestion(long questionId, String conditionId) {
        return questionDao.getById(conditionId, questionId);
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

        long id = snowflake.nextId();
        question.setQuestionId(id);

        questionDao.save(question);

        return this.getQuestion(id, question.getConditionId());
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
        response.setResponseId(snowflake.nextId());
        response.setTimestamp(Instant.now());

        this.userResponseDao.save(response);
        return this.userResponseDao.getById(response.getUserId(), response.getConditionId(), response.getResponseId());
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

    @Override
    public List<PatientStatus> getRecentPatientStatuses(ListPatientStatusRequest request) {
        Instant currentTime = Instant.now();
        Instant maxTime = currentTime.minusSeconds((long) request.getDays() * 24 * 60 * 60);

        long timestampToSearchUntil = maxTime.toEpochMilli() - this.snowflake.getCustomEpoch();
        long firstPossibleId = snowflake.firstSnowflakeAtTimestamp(timestampToSearchUntil);

        List<UserResponse> responses =  this.userResponseDao.getUserResponses(request.getPatientId(), request.getConditionId(),
                firstPossibleId).all();

        ConditionCheckIn checkInDetails = this.conditionCheckInDao.getById(request.getConditionId());

        int currentDayOfYear = 0;
        int totalScore = 0;
        List<PatientStatus> patientStatuses = new ArrayList<>();

        if(!responses.isEmpty()) {
            Instant timestamp = responses.get(0).getTimestamp();
            currentDayOfYear = timestamp.atZone(ZoneId.systemDefault()).getDayOfYear();
        }

        for(Iterator<UserResponse> it = responses.iterator(); it.hasNext(); ) {
            UserResponse response = it.next();

                 Instant timestamp = response.getTimestamp();
                 int dayOfYear = timestamp.atZone(ZoneId.systemDefault()).getDayOfYear();

                 ConditionStatus status;
                 if(dayOfYear > currentDayOfYear || !it.hasNext()) {
                     if(totalScore >= checkInDetails.getUnwellScore()) {
                         status = ConditionStatus.unwell;
                     } else if (totalScore >= checkInDetails.getSubclinicalScore()) {
                         status = ConditionStatus.Subclinical;
                     } else {
                         status = ConditionStatus.Normal;
                     }

                     PatientStatus patientStatus = new PatientStatus(status, totalScore, Date.from(timestamp));
                     patientStatuses.add(patientStatus);

                     currentDayOfYear = dayOfYear;
                     totalScore = 0;
                 }

                 totalScore += response.getAnswer().getAnswerScore();
        }

        return patientStatuses;
    }

    @Override
    public void addCheckIn(ConditionCheckIn checkIn) {
        this.conditionCheckInDao.save(checkIn);
    }

    @Override
    public void updateCheckIn(ConditionCheckIn checkIn) {
       this.conditionCheckInDao.update(checkIn);
    }

    @Override
    public PatientStatus getCurrentStatus(String conditionId, String patientId) {

       UserResponse mostRecentResponse = this.userResponseDao.getMostRecentResponse(patientId, conditionId);

        long currentDayEarliestTimestamp = mostRecentResponse.getTimestamp().toEpochMilli() - this.snowflake.getCustomEpoch();

        long firstPossibleId = snowflake.firstSnowflakeAtTimestamp(currentDayEarliestTimestamp);

        List<UserResponse> responses =  this.userResponseDao.getUserResponses(patientId, conditionId,
                firstPossibleId).all();

        int score = 0;
        for (UserResponse response: responses) {
            score += response.getAnswer().getAnswerScore();
        }

        ConditionCheckIn checkInDetails = this.conditionCheckInDao.getById(conditionId);

        ConditionStatus status;
        if(score >= checkInDetails.getUnwellScore()) {
            status = ConditionStatus.unwell;
        } else if (score >= checkInDetails.getSubclinicalScore()) {
            status = ConditionStatus.Subclinical;
        } else {
            status = ConditionStatus.Normal;
        }

        return new PatientStatus(status, score, Date.from(responses.get(0).getTimestamp()));
    }
}
