package model;

import java.util.ArrayList;

public class Quiz {

    final static public int DEFAULT_QUIZ = -1;

    private int quizId;
    private String quizName;
    private double cesuur;
    private int courseId;
    private ArrayList<Question> questions;

    public Quiz() {
        quizId = DEFAULT_QUIZ;
    }

    public Quiz(String quizName, double cesuur) {
        this.quizId = getQuizId();
        this.quizName = quizName;
        this.cesuur = cesuur;
       // this.courseId = courseId;
        this.questions = new ArrayList<>();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public int getCourseId() {
        return courseId;
    }

    public double getCesuur() {
        return cesuur;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
