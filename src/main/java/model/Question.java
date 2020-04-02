package model;

import java.util.ArrayList;

public class Question {


    private int questionId;
    private String question;
    private ArrayList<Answer> answers;
    Quiz quiz;




    public Question() {
    }

    public Question(String question) {

        this.question = question;
        answers = new ArrayList<>();


    }

    public int getQuestionId() {
        return questionId;
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



}
