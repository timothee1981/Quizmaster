package model;

import java.util.ArrayList;

public class Question {
    final static public int DEFAULT_VRAAG = -1;


    private int questionId;
    private String question;
    private ArrayList<Answer> answers;
    private Quiz quiz;




    public Question() {
        questionId = DEFAULT_VRAAG;
    }

    public Question(String question) {
        getQuestionId();
        this.question = question;
        answers = new ArrayList<>();
        this.quiz = getQuiz();


    }

    public Question(int questionId, String question) {
        this.questionId = questionId;
        this.question = question;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }


    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getQuestion() {
        return question;
    }


    public Quiz getQuiz() {
        return quiz;
    }



    public void voegAntwoordAanVraag(Answer answer){

        answers.add(answer);
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {

      return question;
    }



}
