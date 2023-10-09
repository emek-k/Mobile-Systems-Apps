package com.example.quiz;

public class Question {
    private int questionId;
    private boolean questionAnswer;

    public Question(int questionId, boolean questionAnswer){
        this.questionId = questionId;
        this.questionAnswer = questionAnswer;
    }

    public boolean isTrueAnswer(){
        return this.questionAnswer;
    }

    public int getQuestionId(){
        return this.questionId;
    }
}
