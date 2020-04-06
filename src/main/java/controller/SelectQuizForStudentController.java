package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Quiz;
import view.Main;

import java.util.ArrayList;

public class SelectQuizForStudentController {
    DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
    QuizDAO quizDAO;

    @FXML
    ListView<Quiz> quizList;

    public void setup() {

        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        quizList.getItems().clear();
        ArrayList<Quiz> getAllQuiz = quizDAO.getAll();
        for(Quiz quiz: getAllQuiz){
            quizList.getItems().add(quiz);
        }
    }

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
    }

    public void doQuiz() {
        Quiz quiz = (Quiz) quizList.getSelectionModel().getSelectedItem();
        if(quiz == null){ foulmeldingGeenQuiz();}
        Main.getSceneManager().showFillOutQuiz(quiz);
    }

    private void foulmeldingGeenQuiz() {
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText("Je moet een quiz aanklikken\n");
        foutmelding.show();
        return;
    }
}
