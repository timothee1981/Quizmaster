package model;

import java.util.ArrayList;

public class Question {

    private int questionId;
    private String question;
    private ArrayList<Answer> answers;
    private Answer goedAnswer;


    public Question(String question) {
        this.questionId += 1;
        this.question = question;
        answers = new ArrayList<>();

    }

    public void voegAntwoordAanVraag(Answer answer){
        answers.add(answer);
    }


}
