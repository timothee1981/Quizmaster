package model;

public class Quiz {

    private int quizId;
    private String quizName;
    private double cesuur;

    public Quiz(int quizId, String quizName, double cesuur) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.cesuur = cesuur;
    }

    public int getQuizId() {
        return quizId;
    }
}
