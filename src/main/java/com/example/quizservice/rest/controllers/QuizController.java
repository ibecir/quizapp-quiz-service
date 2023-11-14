package com.example.quizservice.rest.controllers;

import com.example.quizservice.core.model.Quiz;
import com.example.quizservice.core.service.QuizService;
import com.example.quizservice.rest.dto.QuestionResponse;
import com.example.quizservice.rest.dto.QuizRequestDTO;
import com.example.quizservice.rest.dto.QuizResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public ResponseEntity<QuizController> createQuiz(@RequestBody QuizRequestDTO quiz) {
        return new ResponseEntity(quizService.createQuiz(quiz), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable String id) {
        return new ResponseEntity<>(quizService.getQuiz(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<Quiz>> getAllQuizes() {
        return new ResponseEntity<>(quizService.getAllQuizes(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/submit/{quizId}")
    public ResponseEntity<QuizResult> submitQuizResults(@PathVariable String quizId, @RequestBody List<QuestionResponse> responses) {
        return new ResponseEntity<>(quizService.submitQuizResults(quizId, responses), HttpStatus.OK);
    }
}
