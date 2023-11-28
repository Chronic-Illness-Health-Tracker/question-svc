package com.helphi.question.grpc;

import com.helphi.api.Answer;
import com.helphi.api.Question;
import com.helphi.api.grpc.GetConditionQuestionsReply;
import com.helphi.api.grpc.GetConditionQuestionsRequest;
import com.helphi.api.grpc.GetQuestionReply;
import com.helphi.api.grpc.GetQuestionRequest;
import com.helphi.api.grpc.GetUserReponseReply;
import com.helphi.api.grpc.GetUserReponseRequest;
import com.helphi.api.grpc.GetUserReponsesReply;
import com.helphi.api.grpc.GetUserReponsesRequest;
import com.helphi.api.grpc.QuestionServiceGrpc.QuestionServiceImplBase;
import com.helphi.api.grpc.getUsersResponsesForConditionRequest;
import com.helphi.question.svc.QuestionSvc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
@RequiredArgsConstructor
public class GrpcQuestionService extends QuestionServiceImplBase {

    @Autowired
    private final QuestionSvc questionService;

    @Override
    public void getQuestion(GetQuestionRequest request,
        StreamObserver<GetQuestionReply> responseObserver) {
     
        if (request.getQuestionId().isBlank()) {
            responseObserver.onError(
                io.grpc.Status.INVALID_ARGUMENT.withDescription("questionId cannot be empty")
                .asRuntimeException());
            return;
        }

        Question question = questionService.getQuestion(request.getQuestionId());
        Answer answer = question.getAnswer();

        com.helphi.api.grpc.Answer grpcAnswer = com.helphi.api.grpc.Answer.newBuilder()
            .setAnswerId(answer.getAnswerId())
            .setAnswerText(answer.getAnswerText())
            .setAnswerValue(answer.getAnswerValue())
            .build();

        com.helphi.api.grpc.Question grpcQuestion = com.helphi.api.grpc.Question.newBuilder()
            .setConditionId(question.getConditionId())
            .setAnswer(grpcAnswer)
            .build();
                
        GetQuestionReply response = GetQuestionReply.newBuilder()
            .setQuestion(grpcQuestion)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getConditionQuestions(GetConditionQuestionsRequest request,
        StreamObserver<GetConditionQuestionsReply> responseObserver) {

        if (request.getConditionId().isBlank()) {
            responseObserver.onError(
                io.grpc.Status.INVALID_ARGUMENT.withDescription("conditionId cannot be empty")
                .asRuntimeException());
            return;
        }

        List<Question> questions = questionService.getConditionQuestions(request.getConditionId());

        for (Question question : questions) {

            Answer answer = question.getAnswer();

            com.helphi.api.grpc.Answer grpcAnswer = com.helphi.api.grpc.Answer.newBuilder()
                .setAnswerId(answer.getAnswerId())
                .setAnswerText(answer.getAnswerText())
                .setAnswerValue(answer.getAnswerValue())
                .build();

            com.helphi.api.grpc.Question grpcQuestion = com.helphi.api.grpc.Question.newBuilder()
                .setConditionId(question.getConditionId())
                .setAnswer(grpcAnswer)
                .build();
                    
                GetConditionQuestionsReply response = GetConditionQuestionsReply.newBuilder()
                //.setQuestion(grpcQuestion).set
                .build();
            
        }

        GetConditionQuestionsReply response = GetConditionQuestionsReply.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserResponse(GetUserReponseRequest request, 
        StreamObserver<GetUserReponseReply> responseObserver) {

        GetUserReponseReply response = GetUserReponseReply.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersResponses(GetUserReponsesRequest request, 
        StreamObserver<GetUserReponsesReply> responseObserver) {

        GetUserReponsesReply response = GetUserReponsesReply.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsersResponsesForCondition(getUsersResponsesForConditionRequest request, 
        StreamObserver<GetUserReponsesReply> responseObserver) {

        GetUserReponsesReply response = GetUserReponsesReply.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}