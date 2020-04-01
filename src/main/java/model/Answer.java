package model;

public class Answer {
    private int answerId;
    private String answer;
    private Question question;


    public Answer(String answer) {
        this.answerId = 0;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAnswerId() {
        return answerId;
    }

    public Question getQuestion() {
        return question;
    }
}
