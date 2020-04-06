package model;

public class Answer {
    private int answerId;
    private String answer;
    private Question question;



    public Answer(String answer, Question question) {

        this.answer = answer;
        this.question = question;
    }

    public Answer(int answerId, String answer) {
        this.answerId = answerId;
        this.answer = answer;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

    @Override
    public String toString() {
        return String.format("%s",answer);
    }
}
