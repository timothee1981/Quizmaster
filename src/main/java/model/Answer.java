package model;

public class Answer {
    private int answerId;
    private String answer;
    private Question question;


    public Answer(int answerId, String answer, Question question) {
        this.answer = answer;
        this.question = question;
        this.answerId = answerId;
    }
}
