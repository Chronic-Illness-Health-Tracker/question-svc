package com.helphi.question.grpc;

import com.helphi.question.api.grpc.DeleteAllQuestionsRequest;
import com.helphi.question.api.grpc.DeleteQuestionRequest;
import com.helphi.question.api.grpc.DeleteUserResponseRequest;
import com.helphi.question.api.grpc.DeleteUserResponsesForConditionRequest;
import com.helphi.question.api.grpc.DeleteUserResponsesRequest;
import com.helphi.question.api.grpc.GetConditionQuestionsReply;
import com.helphi.question.api.grpc.GetConditionQuestionsRequest;
import com.helphi.question.api.grpc.GetQuestionRequest;
import com.helphi.question.api.grpc.GetUsersResponsesForConditionRequest;
import com.helphi.question.api.grpc.GetUserResponsesReply;
import com.helphi.question.api.grpc.GetUserResponseRequest;
import com.helphi.question.api.grpc.GetUserResponsesRequest;
import com.helphi.question.api.grpc.QuestionRequest;
import com.helphi.question.api.grpc.QuestionServiceGrpc.QuestionServiceImplBase;
import com.helphi.question.api.grpc.RequestReply;
import com.helphi.question.api.Answer;
import com.helphi.question.api.Question;
import com.helphi.question.api.UserResponse;
import com.helphi.question.api.mapper.QuestionMapper;
import com.helphi.question.api.mapper.UserResponseMapper;
import com.helphi.question.svc.IquestionService;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import org.springframework.beans.factory.annotation.Autowired;

/**GRpc server for the question service. 
 * Base autogenerated classes can be found in question-api.
 * To access add question-api as a dependancy in the client project.
 */
@GrpcService
@RequiredArgsConstructor
public class GrpcQuestionService extends QuestionServiceImplBase {

    @Autowired
    private final IquestionService questionService;

    @Override
    public void getQuestion(GetQuestionRequest request,
        StreamObserver<com.helphi.question.api.grpc.Question> responseObserver) {
   
        Question question = questionService.getQuestion(request.getQuestionId(), 
            request.getConditionId());
        QuestionMapper questionMapper = new QuestionMapper();
        com.helphi.question.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(question);

        responseObserver.onNext(grpcQuestion);
        responseObserver.onCompleted();
    }

    @Override
    public void getConditionQuestions(GetConditionQuestionsRequest request,
        StreamObserver<GetConditionQuestionsReply> responseObserver) {

        List<Question> questions = questionService.getConditionQuestions(request.getConditionId());
        GetConditionQuestionsReply response = null;

        List<com.helphi.question.api.grpc.Question> grpcQuestions = new ArrayList<>();
        QuestionMapper questionMapper = new QuestionMapper();

        if (questions != null) {       
            for (Question question : questions) {        
                com.helphi.question.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(question);
                grpcQuestions.add(grpcQuestion);           
            }

            response = GetConditionQuestionsReply.newBuilder()
                .addAllQuestions(grpcQuestions)
                .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserResponse(GetUserResponseRequest request,
                                StreamObserver<com.helphi.question.api.grpc.UserResponse> responseObserver) {

        UserResponse userResponse = questionService.getUserResponse(request.getUserId(), 
            request.getConditionId());

        UserResponseMapper userResponseMapper = new UserResponseMapper();
        com.helphi.question.api.grpc.UserResponse grpcUserResponse = 
            userResponseMapper.mapToGrpc(userResponse);

        responseObserver.onNext(grpcUserResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersResponses(GetUserResponsesRequest request,
                                  StreamObserver<GetUserResponsesReply> responseObserver) {

        GetUserResponsesReply grpcUserResposeReply = null;

        List<UserResponse> userResponses = questionService.getUserResponses(request.getUserId(), 
            request.getTimescale());

        List<com.helphi.question.api.grpc.UserResponse> grpcUserResponses = new ArrayList<>();
        UserResponseMapper userResponseMapper = new UserResponseMapper();

        if (userResponses != null) {
            for (UserResponse userResponse : userResponses) {        
                com.helphi.question.api.grpc.UserResponse grpcUserResponse = 
                    userResponseMapper.mapToGrpc(userResponse);
                grpcUserResponses.add(grpcUserResponse);           
            }

            grpcUserResposeReply = GetUserResponsesReply.newBuilder()
                .addAllResponse(grpcUserResponses)
                .build();
        }

        responseObserver.onNext(grpcUserResposeReply);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersResponsesForCondition(GetUsersResponsesForConditionRequest request, 
        StreamObserver<GetUserResponsesReply> responseObserver) {

        GetUserResponsesReply grpcUserResposeReply = null;

        List<UserResponse> userResponses = questionService.getUsersResponsesForCondition(
            request.getUserId(), request.getConditionId(), request.getTimescale());

        List<com.helphi.question.api.grpc.UserResponse> grpcUserResponses = new ArrayList<>();
        UserResponseMapper userResponseMapper = new UserResponseMapper();

        if (userResponses != null) {
            for (UserResponse userResponse : userResponses) {        
                com.helphi.question.api.grpc.UserResponse grpcUserResponse = 
                    userResponseMapper.mapToGrpc(userResponse);
                grpcUserResponses.add(grpcUserResponse);           
            }

            grpcUserResposeReply = GetUserResponsesReply.newBuilder()
                .addAllResponse(grpcUserResponses)
                .build();
        }   

        responseObserver.onNext(grpcUserResposeReply);
        responseObserver.onCompleted();
    }

    @Override
    public void addQuestion(QuestionRequest request, 
        StreamObserver<com.helphi.question.api.grpc.Question> responseObserver) {

        Answer answerToAdd = new Answer(Long.MIN_VALUE, Long.MIN_VALUE, 
            request.getAnswer().getAnswerText(), Integer.MIN_VALUE);

        Question questionToAdd = new Question(Long.MIN_VALUE, request.getConditionId(), 
            answerToAdd, null);

        Question addedQuestion = questionService.addQuestion(questionToAdd);

        QuestionMapper questionMapper = new QuestionMapper();
        com.helphi.question.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(addedQuestion);

        responseObserver.onNext(grpcQuestion);
        responseObserver.onCompleted();
    }

    @Override
    public void updateQuestion(com.helphi.question.api.grpc.Question request, 
        StreamObserver<com.helphi.question.api.grpc.Question> responseObserver) {
        
        QuestionMapper questionMapper = new QuestionMapper();
        Question question = questionMapper.mapFromGrpc(request);

        Question updatedQuestion = 
            questionService.updateQuestion(request.getQuestionId(), question);

        com.helphi.question.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(updatedQuestion);

        responseObserver.onNext(grpcQuestion);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteQuestion(DeleteQuestionRequest request, 
        StreamObserver<RequestReply> responseObserver) {

        RequestReply deleteResponse = questionService.deleteQuestion(request.getQuestionId());

        responseObserver.onNext(deleteResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllQuestions(DeleteAllQuestionsRequest request, 
        StreamObserver<RequestReply> responseObserver) {
        RequestReply deleteResponse = questionService.deleteAllQuestion(request.getConditionId());

        responseObserver.onNext(deleteResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void addUserResponse(com.helphi.question.api.grpc.UserResponse request, 
        StreamObserver<com.helphi.question.api.grpc.UserResponse> responseObserver) {

        UserResponseMapper userResponseMapper = new UserResponseMapper();
        UserResponse response = userResponseMapper.mapFromGrpc(request);
        UserResponse addedResponse = questionService.addUserResponse(response);

        com.helphi.question.api.grpc.UserResponse grpcAddedResponse = 
            userResponseMapper.mapToGrpc(addedResponse);
        
        responseObserver.onNext(grpcAddedResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUserResponse(com.helphi.question.api.grpc.UserResponse  request, 
        StreamObserver<com.helphi.question.api.grpc.UserResponse> responseObserver) {

        UserResponseMapper userResponseMapper = new UserResponseMapper();
        UserResponse response = userResponseMapper.mapFromGrpc(request);
        UserResponse updatedResponse = questionService.updateUserResponse(response);

        com.helphi.question.api.grpc.UserResponse grpcUpdatedResponse = 
            userResponseMapper.mapToGrpc(updatedResponse);
        
        responseObserver.onNext(grpcUpdatedResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUserResponse(DeleteUserResponseRequest request, 
        StreamObserver<RequestReply> responseObserver) {
        
        RequestReply deleteRequestReply = 
            questionService.deleteUserResponse(request.getResponseId());

        responseObserver.onNext(deleteRequestReply);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUserResponsesForCondition(DeleteUserResponsesForConditionRequest request, 
        StreamObserver<RequestReply> responseObserver) {

        RequestReply deleteRequestReply =
            questionService.deleteUserResponsesForCondition(request.getConditionId());

        responseObserver.onNext(deleteRequestReply);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAllUserResponses(DeleteUserResponsesRequest request, 
        StreamObserver<RequestReply> responseObserver) {
        
        RequestReply deleteRequestReply = 
            questionService.deleteAllUserResponses(request.getUserId());

        responseObserver.onNext(deleteRequestReply);
        responseObserver.onCompleted();
    }
}