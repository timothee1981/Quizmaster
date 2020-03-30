package model;

import java.util.ArrayList;

public class Question {

    private int questionId = 0;
    private String question;
    private ArrayList<Answer> answers;
    private Answer goodAnswer;

    //Auto increment?


    public Question(String question, Answer goodAnswer) {
        this.questionId ++;
        this.question = question;
        this.goodAnswer = goodAnswer;
        answers = new ArrayList<>();

    }

    //

    public void voegAntwoordAanVraag(Answer answer){
        answers.add(goodAnswer);
        answers.add(answer);
    }


}
