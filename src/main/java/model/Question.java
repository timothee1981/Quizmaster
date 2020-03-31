package model;

import java.util.ArrayList;

public class Question {

    private int questionId = 0;
    private String question;
    private ArrayList<String> answers;
    private String goodAnswer;
    private Quiz quiz;

    //Auto increment?


    public Question(int questionId, String question,Quiz quiz) {
        this.questionId = questionId;
        this.question = question;
        this.quiz = quiz;
        answers = new ArrayList<>();

    }


    //methode die

    public void voegAntwoordAanVraag(String answer){
        answers.add(goodAnswer);
        answers.add(answer);
    }


}
