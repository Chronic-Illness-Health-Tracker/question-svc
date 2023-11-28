package com.helphi.question.svc;

import com.helphi.api.Question;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

/** Service for question related business logic.*/
@Service
@RequiredArgsConstructor
public class QuestionSvc {

    // @Autowired
    // private final QuestionDao questionDao;

    public Question getQuestion(String questionId) {
        return null;
    }

    public List<Question> getConditionQuestions(String conditionId) {
        return null;
    }
}
