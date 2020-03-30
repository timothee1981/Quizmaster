package model;

import java.util.ArrayList;

public class Question {

    private int questionId = 0;
    private String question;
    private ArrayList<Answer> answers;
    private Answer goodAnswer;
    private Quiz quiz;

    //Auto increment?


    public Question(int questionId, String question,Quiz quiz, Answer goodAnswer) {
        this.questionId = questionId;
        this.question = question;
        this.quiz = quiz;
        this.goodAnswer = goodAnswer;
        answers = new ArrayList<>();

    }

    //

    public void voegAntwoordAanVraag(Answer answer){
        answers.add(goodAnswer);
        answers.add(answer);
    }


}
