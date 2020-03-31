package model;

import java.util.ArrayList;

public class Question {

    private int questionId;
    private String question;
    private ArrayList<Answer> answers;
    private Answer correctAnswer;




    public Question(int questionId, String question, Answer correctAnswer) {
        this.questionId = questionId;
        this.question = question;
        this.correctAnswer = correctAnswer;
        answers = new ArrayList<>();


    }

    public int getQuestionId() {
        return questionId;
    }

    //methode die

    public void voegAntwoordAanVraag(Answer answer){

        answers.add(answer);
    }


}
