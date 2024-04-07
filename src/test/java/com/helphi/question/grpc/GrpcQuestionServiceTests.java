package com.helphi.question.grpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.helphi.question.api.Answer;
import com.helphi.question.api.Question;
import com.helphi.question.api.UserResponse;
import com.helphi.question.api.grpc.*;
import com.helphi.question.svc.CassandraQuestionSvc;
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

//    @Mock
//    private CassandraQuestionSvc questionSvc;
//
//    private GrpcQuestionService testedClass;
//
//    @BeforeEach
//    public void setup() {
//        testedClass = new GrpcQuestionService(questionSvc);
//    }
//
//    //@Test
//    void getQuestionReturnsCorrectQuestion() throws Exception {
//        Instant timeInstant = Instant.now();
//
//        Mockito.lenient().when(questionSvc.getQuestion(7135745314081738752L,
//            "d4fae303-77a1-41ae-b220-c21ae791cb83"))
//            .thenReturn(
//                new Question(7135745314081738752L,
//                    "d4fae303-77a1-41ae-b220-c21ae791cb83",
//                    new Answer(7135360146863034368L, 7135365497075273728L, "Yes", 2),
//                    timeInstant)
//        );
//
//        GetQuestionRequest request = GetQuestionRequest.newBuilder()
//            .setQuestionId(7135745314081738752L)
//            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.Question> responseObserver = StreamRecorder.create();
//
//        testedClass.getQuestion(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//        assertNull(responseObserver.getError());
//
//        List<com.helphi.question.api.grpc.Question> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        com.helphi.question.api.grpc.Question response = results.get(0);
//
//        assertEquals(com.helphi.question.api.grpc.Question.newBuilder()
//                    .setQuestionId(7135745314081738752L)
//                    .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//                    .setAnswer(com.helphi.question.api.grpc.Answer.newBuilder()
//                        .setAnswerId(7135360146863034368L)
//                        .setQuestionId(7135365497075273728L)
//                        .setAnswerText("Yes")
//                        .setAnswerValue(2)
//                    )
//                    .setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
//                        .setSeconds(timeInstant.getEpochSecond())
//                        .setNanos(timeInstant.getNano())
//                        .build())
//                .build(), response);
//    }
//
//    //@Test
//    void getQuestionReturnsNull() throws Exception {
//        Mockito.lenient().when(questionSvc.getQuestion(7135745314081738752L,
//            "d4fae303-77a1-41ae-b220-c21ae791cb83"))
//            .thenReturn(null);
//
//        GetQuestionRequest request = GetQuestionRequest.newBuilder()
//            .setQuestionId(7135745314081738752L)
//            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.Question> responseObserver = StreamRecorder.create();
//
//        testedClass.getQuestion(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<com.helphi.question.api.grpc.Question> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        com.helphi.question.api.grpc.Question response = results.get(0);
//
//        assertNull(response);
//    }
//
//    @Test
//    public void getConditionQuestionsReturnscorrectQuestions() throws Exception {
//
//        Instant timeInstant = Instant.now();
//        Mockito.lenient()
//            .when(questionSvc.getConditionQuestions("d4fae303-77a1-41ae-b220-c21ae791cb83"))
//                .thenReturn(
//                    new ArrayList<>(Arrays.asList(
//                        new Question(7135745314081738752L,
//                            "d4fae303-77a1-41ae-b220-c21ae791cb83",
//                            new Answer(7135360146863034368L, 7135365497075273728L, "Yes", 2),
//                            timeInstant),
//                        new Question(7135745314981832742L,
//                            "d4fae303-77a1-41ae-b220-c21ae791cb83",
//                            new Answer(7135369502035808256L, 7135369523703582720L, "No", 0),
//                            timeInstant)
//                    ))
//            );
//
//        GetConditionQuestionsRequest request = GetConditionQuestionsRequest.newBuilder()
//            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//            .build();
//
//        StreamRecorder<GetConditionQuestionsReply> responseObserver = StreamRecorder.create();
//
//        testedClass.getConditionQuestions(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<GetConditionQuestionsReply> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        GetConditionQuestionsReply response = results.get(0);
//        List<com.helphi.question.api.grpc.Question> questions = response.getQuestionsList();
//
//        assertEquals(2, questions.size());
//
//        assertEquals(
//            GetConditionQuestionsReply.newBuilder().addAllQuestions(
//            Arrays.asList(
//                com.helphi.question.api.grpc.Question.newBuilder()
//                .setQuestionId(7135745314081738752L)
//                .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//                .setAnswer(com.helphi.question.api.grpc.Answer.newBuilder()
//                    .setAnswerId(7135360146863034368L)
//                    .setQuestionId(7135365497075273728L)
//                    .setAnswerText("Yes")
//                    .setAnswerValue(2)
//                )
//                .setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
//                    .setSeconds(timeInstant.getEpochSecond())
//                    .setNanos(timeInstant.getNano())
//                    .build())
//                .build(),
//                com.helphi.question.api.grpc.Question.newBuilder()
//                .setQuestionId(7135745314981832742L)
//                .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//                .setAnswer(com.helphi.question.api.grpc.Answer.newBuilder()
//                    .setAnswerId(7135369502035808256L)
//                    .setQuestionId(7135369523703582720L)
//                    .setAnswerText("No")
//                    .setAnswerValue(0)
//                )
//                .setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
//                    .setSeconds(timeInstant.getEpochSecond())
//                    .setNanos(timeInstant.getNano())
//                    .build())
//                .build()
//            )).build(), response);
//    }
//
//    @Test
//    public void getConditionQuestionsReturnsNull() throws Exception {
//        Mockito.lenient()
//        .when(questionSvc.getConditionQuestions("d4fae303-77a1-41ae-b220-c21ae791cb83"))
//            .thenReturn(null);
//
//        GetConditionQuestionsRequest request = GetConditionQuestionsRequest.newBuilder()
//            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//            .build();
//
//        StreamRecorder<GetConditionQuestionsReply> responseObserver = StreamRecorder.create();
//
//        testedClass.getConditionQuestions(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<GetConditionQuestionsReply> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        GetConditionQuestionsReply response = results.get(0);
//
//        assertNull(response);
//    }
//
//    @Test
//    public void getUserResponseReturnscorrectResponse() throws Exception {
//
//        Instant timestamp = Instant.now();
//        Mockito.lenient().when(questionSvc.getUserResponse("8dfe2341-6f82-4870-85bc-00f65ce89cae",
//            "d4fae303-77a1-41ae-b220-c21ae791cb83"))
//            .thenReturn(new UserResponse(
//                7135657974130806784L,
//                7135659494918328320L,
//                "1825d5fd-059a-46d6-9d51-c220eba4427f",
//                "a64434b2-0e6d-4b24-99e7-9faeca9237c2",
//                "JoeBloggs",
//                "Yes",
//                2,
//                timestamp,
//                1
//            ));
//
//        GetUserResponseRequest request = GetUserResponseRequest.newBuilder()
//            .setUserId("8dfe2341-6f82-4870-85bc-00f65ce89cae")
//            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.UserResponse> responseObserver = StreamRecorder.create();
//
//        testedClass.getUserResponse(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<com.helphi.question.api.grpc.UserResponse> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        com.helphi.question.api.grpc.UserResponse response = results.get(0);
//
//        assertEquals(com.helphi.question.api.grpc.UserResponse.newBuilder()
//            .setResponseId(7135657974130806784L)
//            .setQuestionId(7135659494918328320L)
//            .setConditionId("1825d5fd-059a-46d6-9d51-c220eba4427f")
//            .setUserId("a64434b2-0e6d-4b24-99e7-9faeca9237c2")
//            .setUserName("JoeBloggs")
//            .setResponseText("Yes")
//            .setResponseValue(2)
//            .setAnswerTimestamp(
//                com.google.protobuf.Timestamp.newBuilder()
//                    .setSeconds(timestamp.getEpochSecond())
//                    .setNanos(timestamp.getNano())
//                    .build()
//            )
//            .setBucket(1)
//            .build(), response);
//    }
//
//    @Test
//    public void getUserResponseReturnsNull() throws Exception {
//        Mockito.lenient().when(questionSvc.getUserResponse("8dfe2341-6f82-4870-85bc-00f65ce89cae",
//            "d4fae303-77a1-41ae-b220-c21ae791cb83"))
//            .thenReturn(null);
//
//        GetUserResponseRequest request = GetUserResponseRequest.newBuilder()
//            .setUserId("8dfe2341-6f82-4870-85bc-00f65ce89cae")
//            .setConditionId("d4fae303-77a1-41ae-b220-c21ae791cb83")
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.UserResponse> responseObserver = StreamRecorder.create();
//
//        testedClass.getUserResponse(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<com.helphi.question.api.grpc.UserResponse> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        com.helphi.question.api.grpc.UserResponse response = results.get(0);
//
//        assertNull(response);
//    }
//
//    @Test
//    public void getUsersResponsesReturnsCorrectResponse() throws Exception {
//        Instant timestampInstant = Instant.now();
//
//        Timescale timescale = Timescale.newBuilder()
//            .setTime(TIME.DAYS)
//            .setDuration(2)
//            .build();
//
//        Mockito.lenient().when(questionSvc.getUserResponses("8dfe2341-6f82-4870-85bc-00f65ce89cae",
//            timescale))
//            .thenReturn(new ArrayList<>(Arrays.asList(
//                UserResponse.builder()
//                .responseId(7136393980224212992L)
//                .questionId(7136394121815527424L)
//                .conditionId("2292f893-dcf4-4756-b727-589167389df9")
//                .userId("afb79788-b05e-47aa-afbc-85c48626f49f")
//                .userName("JoeBloggs")
//                .responseText("Yes")
//                .responseValue(2)
//                .answerTimestamp(timestampInstant)
//                .bucket(1)
//                .build(),
//                UserResponse.builder()
//                .responseId(7136393980224216499L)
//                .questionId(7136394121815277454L)
//                .conditionId("2292a893-dc24-4754-b527-589167389df9")
//                .userId("acb72788-b05e-48aa-bfdc-14c48626f49b")
//                .userName("JoeBloggs")
//                .responseText("No")
//                .responseValue(1)
//                .answerTimestamp(timestampInstant)
//                .bucket(1)
//                .build()
//            )));
//
//        GetUserResponsesRequest request = GetUserResponsesRequest.newBuilder()
//            .setUserId("8dfe2341-6f82-4870-85bc-00f65ce89cae")
//            .setTimescale(timescale)
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.GetUserResponsesReply> responseObserver =
//            StreamRecorder.create();
//
//        testedClass.getUsersResponses(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<GetUserResponsesReply> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//
//        GetUserResponsesReply  response = results.get(0);
//        List<com.helphi.question.api.grpc.UserResponse> questions = response.getResponseList();
//
//        assertEquals(2, questions.size());
//
//        assertEquals(
//            GetUserResponsesReply.newBuilder()
//                .addAllResponse(
//                    Arrays.asList(
//                        com.helphi.question.api.grpc.UserResponse.newBuilder()
//                            .setResponseId(7136393980224212992L)
//                            .setQuestionId(7136394121815527424L)
//                            .setConditionId("2292f893-dcf4-4756-b727-589167389df9")
//                            .setUserId("afb79788-b05e-47aa-afbc-85c48626f49f")
//                            .setUserName("JoeBloggs")
//                            .setResponseText("Yes")
//                            .setResponseValue(2)
//                            .setAnswerTimestamp(
//                                com.google.protobuf.Timestamp.newBuilder()
//                                .setSeconds(timestampInstant.getEpochSecond())
//                                .setNanos(timestampInstant.getNano())
//                                .build()
//                            )
//                            .setBucket(1)
//                            .build(),
//                        com.helphi.question.api.grpc.UserResponse.newBuilder()
//                        .setResponseId(7136393980224216499L)
//                        .setQuestionId(7136394121815277454L)
//                        .setConditionId("2292a893-dc24-4754-b527-589167389df9")
//                        .setUserId("acb72788-b05e-48aa-bfdc-14c48626f49b")
//                        .setUserName("JoeBloggs")
//                        .setResponseText("No")
//                        .setResponseValue(1)
//                        .setAnswerTimestamp(
//                            com.google.protobuf.Timestamp.newBuilder()
//                            .setSeconds(timestampInstant.getEpochSecond())
//                            .setNanos(timestampInstant.getNano())
//                            .build()
//                        )
//                        .setBucket(1)
//                        .build()
//                    )
//                )
//            .build(), response);
//    }
//
//    @Test
//    public void getUsersResponsesReturnsNull() throws Exception {
//        Timescale timescale = Timescale.newBuilder()
//            .setTime(TIME.DAYS)
//            .setDuration(2)
//            .build();
//
//        Mockito.lenient().when(questionSvc.getUserResponses("8dfe2341-6f82-4870-85bc-00f65ce89cae",
//            timescale))
//            .thenReturn(null);
//
//        GetUserResponsesRequest request = GetUserResponsesRequest.newBuilder()
//            .setUserId("8dfe2341-6f82-4870-85bc-00f65ce89cae")
//            .setTimescale(timescale)
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.GetUserResponsesReply> responseObserver =
//            StreamRecorder.create();
//
//        testedClass.getUsersResponses(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<com.helphi.question.api.grpc.GetUserResponsesReply> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        com.helphi.question.api.grpc.GetUserResponsesReply response = results.get(0);
//
//        assertNull(response);
//    }
//
//    @Test
//    public void getUsersResponsesForConditionReturnCorrectValue() throws Exception {
//        Instant timestampInstant = Instant.now();
//
//        Timescale timescale = Timescale.newBuilder()
//            .setTime(TIME.DAYS)
//            .setDuration(2)
//            .build();
//
//        Mockito.lenient().when(questionSvc.getUsersResponsesForCondition(
//            "8dfe2341-6f82-4870-85bc-00f65ce89cae",
//            "2292f893-dcf4-4756-b727-589167389df9", timescale))
//            .thenReturn(new ArrayList<>(Arrays.asList(
//                UserResponse.builder()
//                .responseId(7136393980224216499L)
//                .questionId(7136394121815277454L)
//                .conditionId("2292f893-dcf4-4756-b727-589167389df9")
//                .userId("afb79788-b05e-47aa-afbc-85c48626f49f")
//                .userName("JoeBloggs")
//                .responseText("Yes")
//                .responseValue(2)
//                .answerTimestamp(timestampInstant)
//                .bucket(1)
//                .build(),
//                UserResponse.builder()
//                .responseId(7136393980224212992L)
//                .questionId(7136394121815527424L)
//                .conditionId("2292a893-dc24-4754-b527-589167389df9")
//                .userId("acb72788-b05e-48aa-bfdc-14c48626f49b")
//                .userName("JoeBloggs")
//                .responseText("No")
//                .responseValue(1)
//                .answerTimestamp(timestampInstant)
//                .bucket(1)
//                .build()
//            )));
//
//        GetUsersResponsesForConditionRequest request =
//            GetUsersResponsesForConditionRequest.newBuilder()
//            .setUserId("8dfe2341-6f82-4870-85bc-00f65ce89cae")
//            .setConditionId("2292f893-dcf4-4756-b727-589167389df9")
//            .setTimescale(timescale)
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.GetUserResponsesReply> responseObserver =
//            StreamRecorder.create();
//
//        testedClass.getUsersResponsesForCondition(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<GetUserResponsesReply> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//
//        GetUserResponsesReply  response = results.get(0);
//        List<com.helphi.question.api.grpc.UserResponse> questions = response.getResponseList();
//
//        assertEquals(2, questions.size());
//
//        assertEquals(
//            GetUserResponsesReply.newBuilder()
//                .addAllResponse(
//                    Arrays.asList(
//                        com.helphi.question.api.grpc.UserResponse.newBuilder()
//                            .setResponseId(7136393980224216499L)
//                            .setQuestionId(7136394121815277454L)
//                            .setConditionId("2292f893-dcf4-4756-b727-589167389df9")
//                            .setUserId("afb79788-b05e-47aa-afbc-85c48626f49f")
//                            .setUserName("JoeBloggs")
//                            .setResponseText("Yes")
//                            .setResponseValue(2)
//                            .setAnswerTimestamp(
//                                com.google.protobuf.Timestamp.newBuilder()
//                                .setSeconds(timestampInstant.getEpochSecond())
//                                .setNanos(timestampInstant.getNano())
//                                .build()
//                            )
//                            .setBucket(1)
//                            .build(),
//                        com.helphi.question.api.grpc.UserResponse.newBuilder()
//                        .setResponseId(7136393980224212992L)
//                        .setQuestionId(7136394121815527424L)
//                        .setConditionId("2292a893-dc24-4754-b527-589167389df9")
//                        .setUserId("acb72788-b05e-48aa-bfdc-14c48626f49b")
//                        .setUserName("JoeBloggs")
//                        .setResponseText("No")
//                        .setResponseValue(1)
//                        .setAnswerTimestamp(
//                            com.google.protobuf.Timestamp.newBuilder()
//                            .setSeconds(timestampInstant.getEpochSecond())
//                            .setNanos(timestampInstant.getNano())
//                            .build()
//                        )
//                        .setBucket(1)
//                        .build()
//                    )
//                )
//            .build(), response);
//    }
//
//    @Test
//    public void addQuestionReturnsCorrectResult() throws Exception {
//        Instant timestampInstant = Instant.now();
//
//        Mockito.lenient().when(questionSvc.addQuestion(
//            new Question(Long.MIN_VALUE, "2292f893-dcf4-4756-b727-589167389df9",
//                new Answer(Long.MIN_VALUE, Long.MIN_VALUE, "Yes", Integer.MIN_VALUE),
//                null)
//        )).thenReturn(new Question(7136464066389741568L, "2292f893-dcf4-4756-b727-589167389df9",
//            new Answer(7136464503469772800L, 7136464066389741568L, "Yes", 2),
//            timestampInstant));
//
//        QuestionRequest request = QuestionRequest.newBuilder()
//            .setConditionId("2292f893-dcf4-4756-b727-589167389df9")
//            .setAnswer(com.helphi.question.api.grpc.Answer.newBuilder()
//                .setAnswerText("Yes"))
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.Question> responseObserver = StreamRecorder.create();
//
//        testedClass.addQuestion(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<com.helphi.question.api.grpc.Question> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//
//        com.helphi.question.api.grpc.Question response = results.get(0);
//
//        assertEquals(com.helphi.question.api.grpc.Question.newBuilder()
//            .setQuestionId(7136464066389741568L)
//            .setConditionId("2292f893-dcf4-4756-b727-589167389df9")
//            .setAnswer(com.helphi.question.api.grpc.Answer.newBuilder()
//                .setAnswerId(7136464503469772800L)
//                .setQuestionId(7136464066389741568L)
//                .setAnswerText("Yes")
//                .setAnswerValue(2)
//            )
//            .setCreatedAt(com.google.protobuf.Timestamp.newBuilder()
//                .setSeconds(timestampInstant.getEpochSecond())
//                .setNanos(timestampInstant.getNano())
//                .build())
//            .build(), response);
//    }
//
//    @Test
//    public void addQuestionReturnsNull() throws Exception {
//        Instant timestampInstant = Instant.now();
//
//        Mockito.lenient().when(questionSvc.addQuestion(
//            new Question(Long.MIN_VALUE, "2292f893-dcf4-4756-b727-589167389df9",
//                new Answer(Long.MIN_VALUE, Long.MIN_VALUE, "Yes", Integer.MIN_VALUE),
//                timestampInstant)
//        )).thenReturn(null);
//
//        QuestionRequest request = QuestionRequest.newBuilder()
//            .setConditionId("2292f893-dcf4-4756-b727-589167389df9")
//            .setAnswer(com.helphi.question.api.grpc.Answer.newBuilder()
//                .setAnswerText("Yes"))
//            .build();
//
//        StreamRecorder<com.helphi.question.api.grpc.Question> responseObserver = StreamRecorder.create();
//
//        testedClass.addQuestion(request, responseObserver);
//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }
//
//        assertNull(responseObserver.getError());
//
//        List<com.helphi.question.api.grpc.Question> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        com.helphi.question.api.grpc.Question response = results.get(0);
//
//        assertNull(response);
//    }

}

