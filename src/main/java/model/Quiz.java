package model;

import java.util.ArrayList;

public class Quiz {

    private int quizId;
    private String quizName;
    private double cesuur;
    private Course course;
    private ArrayList<Question> questions;

    public Quiz() {
    }

    public Quiz(int quizId, String quizName, double cesuur) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.cesuur = cesuur;
        this.questions = new ArrayList<>();
    }

    public int getQuizId() {
        return quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public double getCesuur() {
        return cesuur;
    }

    public Course getCourse() {
        return course;
    }

    public ArrayList<Question> voegQuestionAanQuiz(Question question){
        questions.add(question);
        return questions;
    }
}
