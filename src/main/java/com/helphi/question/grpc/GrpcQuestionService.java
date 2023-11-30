package com.helphi.question.grpc;

import com.helphi.api.Answer;
import com.helphi.api.Question;
import com.helphi.api.UserResponse;
import com.helphi.api.grpc.DeleteAllQuestionsRequest;
import com.helphi.api.grpc.DeleteQuestionRequest;
import com.helphi.api.grpc.DeleteUserResponseRequest;
import com.helphi.api.grpc.DeleteUserResponsesForConditionRequest;
import com.helphi.api.grpc.DeleteUserResponsesRequest;
import com.helphi.api.grpc.GetConditionQuestionsReply;
import com.helphi.api.grpc.GetConditionQuestionsRequest;
import com.helphi.api.grpc.GetQuestionRequest;
import com.helphi.api.grpc.GetUserReponseRequest;
import com.helphi.api.grpc.GetUserReponsesReply;
import com.helphi.api.grpc.GetUserReponsesRequest;
import com.helphi.api.grpc.GetUsersResponsesForConditionRequest;
import com.helphi.api.grpc.QuestionRequest;
import com.helphi.api.grpc.QuestionServiceGrpc.QuestionServiceImplBase;
import com.helphi.api.grpc.RequestReply;
import com.helphi.api.mapper.QuestionMapper;
import com.helphi.api.mapper.UserResponseMapper;
import com.helphi.question.svc.QuestionSvc;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**GRpc server for the question service. 
 * Base autogenerated classes can be found in question-api.
 * To access add question-api as a dependancy in the client project.
 */
@GRpcService
@RequiredArgsConstructor
public class GrpcQuestionService extends QuestionServiceImplBase {

    @Autowired
    private final QuestionSvc questionService;

    @Override
    public void getQuestion(GetQuestionRequest request,
        StreamObserver<com.helphi.api.grpc.Question> responseObserver) {
   
        Question question = questionService.getQuestion(request.getQuestionId());
        QuestionMapper questionMapper = new QuestionMapper();
        com.helphi.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(question);

        responseObserver.onNext(grpcQuestion);
        responseObserver.onCompleted();
    }

    @Override
    public void getConditionQuestions(GetConditionQuestionsRequest request,
        StreamObserver<GetConditionQuestionsReply> responseObserver) {

        List<Question> questions = questionService.getConditionQuestions(request.getConditionId());
        GetConditionQuestionsReply response = null;

        List<com.helphi.api.grpc.Question> grpcQuestions = new ArrayList<>();
        QuestionMapper questionMapper = new QuestionMapper();

        if (questions != null) {       
            for (Question question : questions) {        
                com.helphi.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(question);
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
    public void getUserResponse(GetUserReponseRequest request, 
        StreamObserver<com.helphi.api.grpc.UserResponse> responseObserver) {

        UserResponse userResponse = questionService.getUserResponse(request.getUserId(), 
            request.getConditionId());

        UserResponseMapper userResponseMapper = new UserResponseMapper();
        com.helphi.api.grpc.UserResponse grpcUserResponse = 
            userResponseMapper.mapToGrpc(userResponse);

        responseObserver.onNext(grpcUserResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersResponses(GetUserReponsesRequest request, 
        StreamObserver<GetUserReponsesReply> responseObserver) {

        GetUserReponsesReply grpcUserResposeReply = null;

        List<UserResponse> userResponses = questionService.getUserResponses(request.getUserId(), 
            request.getTimescale());

        List<com.helphi.api.grpc.UserResponse> grpcUserResponses = new ArrayList<>();
        UserResponseMapper userResponseMapper = new UserResponseMapper();

        if (userResponses != null) {
            for (UserResponse userResponse : userResponses) {        
                com.helphi.api.grpc.UserResponse grpcUserResponse = 
                    userResponseMapper.mapToGrpc(userResponse);
                grpcUserResponses.add(grpcUserResponse);           
            }

            grpcUserResposeReply = GetUserReponsesReply.newBuilder()
                .addAllResponse(grpcUserResponses)
                .build();
        }

        responseObserver.onNext(grpcUserResposeReply);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersResponsesForCondition(GetUsersResponsesForConditionRequest request, 
        StreamObserver<GetUserReponsesReply> responseObserver) {

        GetUserReponsesReply grpcUserResposeReply = null;

        List<UserResponse> userResponses = questionService.getUsersResponsesForCondition(
            request.getUserId(), request.getConditionId(), request.getTimescale());

        List<com.helphi.api.grpc.UserResponse> grpcUserResponses = new ArrayList<>();
        UserResponseMapper userResponseMapper = new UserResponseMapper();

        if (userResponses != null) {
            for (UserResponse userResponse : userResponses) {        
                com.helphi.api.grpc.UserResponse grpcUserResponse = 
                    userResponseMapper.mapToGrpc(userResponse);
                grpcUserResponses.add(grpcUserResponse);           
            }

            grpcUserResposeReply = GetUserReponsesReply.newBuilder()
                .addAllResponse(grpcUserResponses)
                .build();
        }   

        responseObserver.onNext(grpcUserResposeReply);
        responseObserver.onCompleted();
    }

    @Override
    public void addQuestion(QuestionRequest request, 
        StreamObserver<com.helphi.api.grpc.Question> responseObserver) {

        Answer answerToAdd = new Answer(-1L, -1L, request.getAnswer().getAnswerText(), 
                request.getAnswer().getAnswerValue());

        Question questionToAdd = new Question(-1L, request.getConditionId(), answerToAdd);

        Question addedQuestion = questionService.addQuestion(questionToAdd);

        QuestionMapper questionMapper = new QuestionMapper();
        com.helphi.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(addedQuestion);

        responseObserver.onNext(grpcQuestion);
        responseObserver.onCompleted();
    }

    @Override
    public void updateQuestion(com.helphi.api.grpc.Question request, 
        StreamObserver<com.helphi.api.grpc.Question> responseObserver) {
        
        QuestionMapper questionMapper = new QuestionMapper();
        Question question = questionMapper.mapFromGrpc(request);

        Question updatedQuestion = 
            questionService.updateQuestion(request.getQuestionId(), question);

        com.helphi.api.grpc.Question grpcQuestion = questionMapper.mapToGrpc(updatedQuestion);

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
    public void addUserResponse(com.helphi.api.grpc.UserResponse request, 
        StreamObserver<com.helphi.api.grpc.UserResponse> responseObserver) {

        UserResponseMapper userResponseMapper = new UserResponseMapper();
        UserResponse response = userResponseMapper.mapFromGrpc(request);
        UserResponse addedResponse = questionService.addUserResponse(response);

        com.helphi.api.grpc.UserResponse grpcAddedResponse = 
            userResponseMapper.mapToGrpc(addedResponse);
        
        responseObserver.onNext(grpcAddedResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUserResponse(com.helphi.api.grpc.UserResponse  request, 
        StreamObserver<com.helphi.api.grpc.UserResponse> responseObserver) {

        UserResponseMapper userResponseMapper = new UserResponseMapper();
        UserResponse response = userResponseMapper.mapFromGrpc(request);
        UserResponse updatedResponse = questionService.updateUserResponse(response);

        com.helphi.api.grpc.UserResponse grpcUpdatedResponse = 
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