package model;

import java.util.ArrayList;

public class Quiz {

    final static public int DEFAULT_QUIZ = -1;

    private int quizId;
    private String quizName;
    private double cesuur;
    private Course course;
    private ArrayList<Question> questions;

    public Quiz() {
        quizId =DEFAULT_QUIZ;
    }

    public Quiz(String quizName, double cesuur) {
        this.quizId = getQuizId();
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

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {

        return quizName;
    }
}
