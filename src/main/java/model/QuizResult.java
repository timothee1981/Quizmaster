package model;

import java.util.Date;

public class QuizResult {
    private Quiz quiz;
    private Student student;
    private int antwoordenJuist;
    private Date datumgemaakt;
    private String aantwoordGebruiker;


    public QuizResult(Quiz quiz, Student student) {
        this.quiz = quiz;
        this.student = student;
        this.datumgemaakt = new Date();



    }



    public Quiz getQuiz() {
        return quiz;
    }

    public Student getStudent() {
        return student;
    }


    public int getAantalvragen() {
        return quiz.getQuestions().size();
    }


    //methode om uithalen hoeveel aantwoorden student goed beantwoord heeft, string aantwoordgebruiker als parameter
        //geef int terug
        //check de vraag string met goede vraag
        //als equal: aantwoorden juist ++



    //maak methode geef score terug //parameter juisteantwoord
      //geef terug score terug
        //check totaal aantal vragen in quiz
        //op basis van vragen en goed antwoorden geef de score in pct terug


    //vergelijk met cesuur--> als boven cesuur: quiz geslaagd...parameter score
            //geef boolean terug: quiz geslaagd
            //score > cesuur ->>>> geslaagd


    //check de quiz cesuur
    /*public void setAantalvragen(int aantalvragen) {
        this.aantalvragen = aantalvragen;
    }*/
}
