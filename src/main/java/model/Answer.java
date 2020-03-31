package model;

public class Answer {
    private int answerId;
    private String answer;
    private Question question;

    public Answer(int answerId, String answer) {
        this.answerId = answerId;
        this.answer = answer;
    }


    public int getAnswerId() {
        return answerId;
    }
}
