package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return answerId == answer1.answerId &&
                answer.equals(answer1.answer) &&
                question.equals(answer1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, answer, question);
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
