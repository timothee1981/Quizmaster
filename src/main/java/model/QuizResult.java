package model;

import java.util.Date;

public class QuizResult {
    private Quiz quiz;
    private Student student;
    private int antwoordenJuist;
    private Date datumgemaakt;
    private String antwoordGebruiker;
    private boolean gehaald = true;


    public QuizResult(Quiz quiz, Student student) {
        this.quiz = quiz;
        this.student = student;
        this.datumgemaakt = new Date();
        antwoordenJuist = getAntwoordenJuist();




    }

    public int getAntwoordenJuist() {
        return antwoordenJuist;
    }

    public void setAntwoordenJuist(int antwoordenJuist) {
        this.antwoordenJuist = antwoordenJuist;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Student getStudent() {
        return student;
    }


    //methode om antwoord te vergelijken, string aantwoordgebruiker als parameter
        //geef int terug
        //check de vraag string met goede vraag
        //als equal: aantwoorden juist ++

    public double scoreQuiz(int antwoordenJuist){
        double score;
        int antalantwoorden = quiz.getQuestions().size();
        score = antwoordenJuist / antalantwoorden *100;
        return score;
    }

    public boolean isgeslaagd(double score) {
        if(score < quiz.getCesuur()) {
            return gehaald = true;
        }else
            return gehaald =  false;
    }





}
