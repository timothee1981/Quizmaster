package model;

import java.util.ArrayList;
import java.util.Objects;

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

    public Quiz(int quizId) {
        this.quizId = quizId;
    }

    public Quiz(String quizName, double cesuur) {
        this.quizId = getQuizId();
        this.quizName = quizName;
        this.cesuur = cesuur;
        this.questions = new ArrayList<>();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return
                quizName.equals(quiz.quizName) &&
                Double.compare(quiz.cesuur, cesuur) == 0 ;
              //  courseId == quiz.courseId;


         //       questions.equals(quiz.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, quizName, cesuur, courseId, questions);
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
