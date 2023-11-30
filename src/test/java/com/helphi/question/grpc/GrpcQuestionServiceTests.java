package com.helphi.question.grpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.helphi.api.Answer;
import com.helphi.api.Question;
import com.helphi.api.UserResponse;
import com.helphi.api.grpc.GetConditionQuestionsReply;
import com.helphi.api.grpc.GetConditionQuestionsRequest;
import com.helphi.api.grpc.GetQuestionRequest;
import com.helphi.api.grpc.GetUserReponseRequest;
import com.helphi.question.svc.QuestionSvc;
import io.grpc.internal.testing.StreamRecorder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class GrpcQuestionServiceTests{

    @Mock
    private QuestionSvc questionSvc;

    private GrpcQuestionService testedClass;

    @BeforeEach
    public void setup() {
        testedClass = new GrpcQuestionService(questionSvc);
    }

    @Test
    void getQuestionReturnsCorrectQuestion() throws Exception {
        Mockito.lenient().when(questionSvc.getQuestion(7135745314081738752L))
            .thenReturn(
                new Question(7135745314081738752L, 
                    "d4fae303-77a1-41ae-b220-c21ae791cb83",
                    new Answer(7135360146863034368L, 7135365497075273728L, "Yes", 2))
        );

        GetQuestionRequest request = GetQuestionRequest.newBuilder()
            .setQuestionId(7135745314081738752L)
            .build();

        StreamRecorder<com.helphi.api.grpc.Question> responseObserver = StreamRecorder.create();

        testedClass.getQuestion(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }
        assertNull(responseObserver.getError());

        List<com.helphi.api.grpc.Question> results = responseObserver.getValues();
        assertEquals(1, results.size());
        com.helphi.api.grpc.Question response = results.get(0);

        assertEquals(com.helphi.api.grpc.Question.newBuilder()
                    .setQuestionId(7135745314081738752L)
                    .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
                    .setAnswer(com.helphi.api.grpc.Answer.newBuilder()
                        .setAnswerId(7135360146863034368L)
                        .setQuestionId(7135365497075273728L)
                        .setAnswerText("Yes")
                        .setAnswerValue(2)
                    )
                .build(), response);
    }  

    @Test
    void getQuestionReturnsNull() throws Exception {
        Mockito.lenient().when(questionSvc.getQuestion(7135745314081738752L))
            .thenReturn(null);

        GetQuestionRequest request = GetQuestionRequest.newBuilder()
            .setQuestionId(7135745314081738752L)
            .build();

        StreamRecorder<com.helphi.api.grpc.Question> responseObserver = StreamRecorder.create();

        testedClass.getQuestion(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());

        List<com.helphi.api.grpc.Question> results = responseObserver.getValues();
        assertEquals(1, results.size());
        com.helphi.api.grpc.Question response = results.get(0);

        assertNull(response);
    }

    @Test
    public void getConditionQuestionsReturnscorrectQuestions() throws Exception {
        Mockito.lenient()
            .when(questionSvc.getConditionQuestions("d4fae303-77a1-41ae-b220-c21ae791cb83"))
                .thenReturn(
                    new ArrayList<>(Arrays.asList(
                        new Question(7135745314081738752L, 
                            "d4fae303-77a1-41ae-b220-c21ae791cb83",
                            new Answer(7135360146863034368L, 7135365497075273728L, "Yes", 2)),
                        new Question(7135745314981832742L, 
                            "d4fae303-77a1-41ae-b220-c21ae791cb83",
                            new Answer(7135369502035808256L, 7135369523703582720L, "No", 0))
                    ))
            );

        GetConditionQuestionsRequest request = GetConditionQuestionsRequest.newBuilder()
            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
            .build();

        StreamRecorder<GetConditionQuestionsReply> responseObserver = StreamRecorder.create();

        testedClass.getConditionQuestions(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());

        List<GetConditionQuestionsReply> results = responseObserver.getValues();
        assertEquals(1, results.size());
        GetConditionQuestionsReply response = results.get(0);
        List<com.helphi.api.grpc.Question> questions = response.getQuestionsList();

        assertEquals(2, questions.size());

        assertEquals(
            GetConditionQuestionsReply.newBuilder().addAllQuestions(
            Arrays.asList(
                com.helphi.api.grpc.Question.newBuilder()
                .setQuestionId(7135745314081738752L)
                .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
                .setAnswer(com.helphi.api.grpc.Answer.newBuilder()
                    .setAnswerId(7135360146863034368L)
                    .setQuestionId(7135365497075273728L)
                    .setAnswerText("Yes")
                    .setAnswerValue(2)
                ).build(),       
                com.helphi.api.grpc.Question.newBuilder()
                    .setQuestionId(7135745314981832742L)
                    .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
                    .setAnswer(com.helphi.api.grpc.Answer.newBuilder()
                        .setAnswerId(7135369502035808256L)
                        .setQuestionId(7135369523703582720L)
                        .setAnswerText("No")
                        .setAnswerValue(0)
                    )
                .build()
            )).build(), response);
    }

    @Test
    public void getConditionQuestionsReturnsNull() throws Exception {
        Mockito.lenient()
        .when(questionSvc.getConditionQuestions("d4fae303-77a1-41ae-b220-c21ae791cb83"))
            .thenReturn(null);

        GetConditionQuestionsRequest request = GetConditionQuestionsRequest.newBuilder()
            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
            .build();

        StreamRecorder<GetConditionQuestionsReply> responseObserver = StreamRecorder.create();

        testedClass.getConditionQuestions(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());

        List<GetConditionQuestionsReply> results = responseObserver.getValues();
        assertEquals(1, results.size());
        GetConditionQuestionsReply response = results.get(0);

        assertNull(response);
    }

    @Test
    public void getUserResponseReturnscorrectResponse() throws Exception {

        Instant timestamp = Instant.now();
        Mockito.lenient().when(questionSvc.getUserResponse("8dfe2341-6f82-4870-85bc-00f65ce89cae",
            "d4fae303-77a1-41ae-b220-c21ae791cb83"))
            .thenReturn(new UserResponse(
                7135657974130806784L, 
                7135659494918328320L, 
                "1825d5fd-059a-46d6-9d51-c220eba4427f",
                "a64434b2-0e6d-4b24-99e7-9faeca9237c2",
                "JoeBloggs", 
                "Yes", 
                2,
                timestamp,
                1
            ));

        GetUserReponseRequest request = GetUserReponseRequest.newBuilder()
            .setUserId("8dfe2341-6f82-4870-85bc-00f65ce89cae")
            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
            .build();

        StreamRecorder<com.helphi.api.grpc.UserResponse> responseObserver = StreamRecorder.create();

        testedClass.getUserResponse(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());

        List<com.helphi.api.grpc.UserResponse> results = responseObserver.getValues();
        assertEquals(1, results.size());
        com.helphi.api.grpc.UserResponse response = results.get(0);

        assertEquals(com.helphi.api.grpc.UserResponse.newBuilder()
            .setResponseId(7135657974130806784L)
            .setQuestionId(7135659494918328320L)
            .setConditionId("1825d5fd-059a-46d6-9d51-c220eba4427f")
            .setUserId("a64434b2-0e6d-4b24-99e7-9faeca9237c2")
            .setUserName("JoeBloggs")
            .setResponseText("Yes")
            .setResponseValue(2)
            .setAnswerTimestamp(
                com.google.protobuf.Timestamp.newBuilder()
                    .setSeconds(timestamp.getEpochSecond())
                    .setNanos(timestamp.getNano())
                    .build()
            )
            .setBucket(1)
            .build(), response);
    }
}
