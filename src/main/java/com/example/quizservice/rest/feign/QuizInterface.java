package com.example.quizservice.rest.feign;

import com.example.quizservice.core.model.Question;
import com.example.quizservice.core.model.enums.Category;
import com.example.quizservice.rest.dto.QuestionResponse;
import com.example.quizservice.rest.dto.QuestionsScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @RequestMapping(method = RequestMethod.GET, path = "/questions/generate")
    public ResponseEntity<List<Question>> generateQuestions(@RequestParam Category category, @RequestParam int numOfQuestions);

    @RequestMapping(method = RequestMethod.GET, path = "/questions/{questionIds}")
    public ResponseEntity<List<Question>> getQuestionsByIds(@PathVariable String[] questionIds);

    @RequestMapping(method = RequestMethod.POST, path = "/questions/submit")
    public ResponseEntity<QuestionsScore> submitAnswers(@RequestBody List<QuestionResponse> responses);
}
