package com.example.quizservice.core.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Quiz {
    private String id;
    private String title;
    private List<Question> questions;

    public Quiz() {
    }

    public Quiz(String id, String title, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
