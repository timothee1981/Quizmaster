package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Quiz;
import view.Main;

public class FillOutQuizController {
    DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
    QuestionDAO questionDAO;
    QuizDAO quizDAO;

    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;

    public void setup(Quiz quiz) {
        dbAccess.openConnection();
        this.questionDAO = new QuestionDAO(dbAccess);
        questionArea.appendText(quiz.getQuestions().get(0).getQuestion());


        //geef quiz door die gemaakt wordt
        //geef al bijhorende aantwoorden, in een wilkeurige volgorde

    }

    public void doRegisterA() {
        //kies aantwoord A ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doRegisterB() {
        //kies aantwoord B ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doRegisterC() {
        //kies aantwoord C ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doRegisterD() {
        //kies aantwoord D ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doNextQuestion() {
        //ga na volgend vraag in de quiz....setup methode aanroep
    }

    public void doPreviousQuestion() {
        //ga na vorige vraag in de quiz.....setup methode aanroep
    }

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showManageUserScene();
    }
}
