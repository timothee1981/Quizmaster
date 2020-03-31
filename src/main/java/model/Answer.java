package model;

public class Answer {
    private int answerId;
    private String answer;



    public Answer(int answerId, String answer) {
        this.answer = answer;
        this.answerId = answerId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getAnswer() {
        return answer;
    }
}