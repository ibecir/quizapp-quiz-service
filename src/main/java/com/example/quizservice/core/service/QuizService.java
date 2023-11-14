package com.example.quizservice.core.service;

import com.example.quizservice.core.model.Question;
import com.example.quizservice.core.model.Quiz;
import com.example.quizservice.core.model.enums.Category;
import com.example.quizservice.core.repository.QuizRepository;
import com.example.quizservice.rest.dto.QuestionResponse;
import com.example.quizservice.rest.dto.QuestionsScore;
import com.example.quizservice.rest.dto.QuizRequestDTO;
import com.example.quizservice.rest.dto.QuizResult;
import com.example.quizservice.rest.feign.QuizInterface;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizInterface quizInterface;

    public QuizService(QuizRepository quizRepository, QuizInterface quizInterface) {
        this.quizRepository = quizRepository;
        this.quizInterface = quizInterface;
    }

    public Quiz createQuiz(QuizRequestDTO quizRequest) {
        // We now need to interact with the QuestionService in order to get the questions
        // by using the RestTemplate http://localhost:8080/questions/generate
        // How to know the server, how to identify the port because of the microservices
        // We have to use the Feign client - http request nad provides the flexible way to declare the apis we need,
        // We also need ServiceDiscovery to identify services - EurekaServer
        List<Question> questions = quizInterface.generateQuestions(Category.JAVA, 2).getBody(); // via Feign make a request

        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(String id) {
        System.out.println("CAME AND IT IS " + id);
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()){
            System.out.println("FOUND---------->");
            return quiz.get();
        }
        return null;
    }

    public QuizResult submitQuizResults(String quizId, List<QuestionResponse> responses){
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if(quiz.isEmpty())
            throw new NotFoundException();

        QuestionsScore score = quizInterface.submitAnswers(responses).getBody();

        return new QuizResult(score.getMessage(), score.getScore());
    }

    public List<Quiz> getAllQuizes() {
        return quizRepository.findAll();
    }
}
