package controller;

import com.mysql.cj.util.Util;
import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Answer;
import model.Question;
import model.Quiz;
import model.QuizResult;
import view.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FillOutQuizController {

    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;
    @FXML
    private Label  quizIdLabel,questionIdLabel;

    private Quiz quiz;
    private int currentQuestion;
    private ArrayList<Answer> currentAnswerOrder;

    public void setup(Quiz inputQuiz) {

        // store Quiz as global data - so questions can be accessed
        quiz = inputQuiz;

        // check of de quiz vragen heeft, zo niet, dan foutmelding en wordt je teruggestuurd naar selectie-pagina
        if(quiz.getQuestions().size() == 0){
            UsefullStuff.showInformationMessage("Deze quiz heeft geen vragen\nJe wordt terug gestuurd naar de quiz-selectie-pagina");
            Main.getSceneManager().showSelectQuizForStudent();
            return;
        }

        // set current question to 1
        currentQuestion = 1;

        // toon huidige vraag
        showQuestion();
    }

    private void showQuestion() {
        // get question that needs to be shown
        Question questionToShow = quiz.getQuestions().get(currentQuestion-1);

        // update label:
        titleLabel.setText(String.format("Vraag %d", currentQuestion));

        // clear working area
        questionArea.clear();
        // show question number:
        questionArea.appendText(String.format("Vraag: "));
        // show question
        questionArea.appendText(questionToShow + "\n\n");

        ArrayList<Answer> answerListShuffled = questionToShow.getAnswers();
        Collections.shuffle(answerListShuffled);
        // save shuffledAnswer for later checking-purpose
        currentAnswerOrder = answerListShuffled;
        // show answers
        for(Answer answer: answerListShuffled){
            questionArea.appendText(answer.toString() + "\n");
        }
    }

    public void doRegisterA() {
        int answerIndex = 0;
        checkAnswer(answerIndex);
    }

    public void doRegisterB() {
        int answerIndex = 1;
        checkAnswer(answerIndex);
    }

    public void doRegisterC() {
        int answerIndex = 2;
        checkAnswer(answerIndex);
    }

    public void doRegisterD() {
        int answerIndex = 0;
        checkAnswer(answerIndex);
    }

    private void checkAnswer(int answerIndex) {
        // get correct answer
        Answer correctAnswer = quiz.getQuestions().get(currentQuestion-1).getCorrectAnswer();
        Answer filledOutAnswer = currentAnswerOrder.get(answerIndex);

        //todo: nog iets met bijhouden van juist antwoorden

        if(correctAnswer.equals(filledOutAnswer)){
            // antwoorden zijn gelijk - HOEZEE
            UsefullStuff.showInformationMessage("Het antwoord is juist!!!\nJe gaat nu door naar de volgende vraag");
        } else {
            UsefullStuff.showInformationMessage("Het antwoord is onjuist!!!\nJe gaat nu door naar de volgende vraag");
        }

        // check of dit de laatste vraag is
        if(currentQuestion == quiz.getQuestions().size()){
            // dit is de laatste vraag
            // ga niet door naar de volgend vraag
            UsefullStuff.showInformationMessage("Dit is de laatste vraag");
        } else{
            doNextQuestion();
        }
    }

    public void doNextQuestion() {
        // check of dit de laatste vraag is
        if(currentQuestion == quiz.getQuestions().size()){
            UsefullStuff.showInformationMessage("Dit is de laatste vraag\n je kan niet verder");
            return;
        }
        //als er nog vragen zijn, toon dan de volgende vraag
        currentQuestion++;
        showQuestion();
    }

    public void doPreviousQuestion() {

        // check of dit de eerste vraag is
        if(currentQuestion == 1){
            UsefullStuff.showInformationMessage("Dit is de eerste vraag\n je kan niet verder terug");
            return;
        }
        //als er nog vragen zijn, toon dan de volgende vraag
        currentQuestion--;
        showQuestion();
    }

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
    }
}
