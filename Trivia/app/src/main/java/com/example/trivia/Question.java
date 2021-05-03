package com.example.trivia;

import java.util.ArrayList;
import java.util.Collections;

//This fragment stores all question info
public class Question {
    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;
    private String selectedAnswer;

    public Question(String question, String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public Question(String question, String correctAnswer, ArrayList<String> incorrectAnswers, String selectedAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.selectedAnswer = selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    //Shuffles answers to stop correct answer being same button every time
    public ArrayList<String> getShuffledAnswers(){
        ArrayList<String> answers = new ArrayList<>(incorrectAnswers);
        answers.add(correctAnswer);
        Collections.shuffle(answers);
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }
}
