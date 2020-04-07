package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Quiz;
import view.Main;

import java.util.ArrayList;

public class ManageQuizzesController {
    QuizDAO quizDAO;
    DBAccess dbAccess;

    @FXML
    private ListView quizList;

    public void setup() {
        dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);


        // vul quizlijst met quiz-items
        quizList.getItems().clear();
        ArrayList<Quiz> getAllQuiz = quizDAO.getAll();
        for(Quiz quiz: getAllQuiz){
            quizList.getItems().add(quiz);
        }
    }

    public void doMenu(){
        Main.getSceneManager().showWelcomeScene();
        dbAccess.closeConnection();
    }

    public void doCreateQuiz(){
        Quiz quiz = new Quiz();
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }

    public void doUpdateQuiz(){
        Quiz quiz = (Quiz) quizList.getSelectionModel().getSelectedItem();

        if(quiz == null){
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een vraag aanklikken\n");
            foutmelding.show();
            return;
        }

        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }

    public void doDeleteQuiz(){
        dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();

        Quiz quiz = null;
        quiz = (Quiz) quizList.getSelectionModel().getSelectedItem();
        if(quiz == null){
            showErrorMessage("Selecteer een quiz");
            return;
        }

        quizDAO.deleteQuiz(quiz);

        showInformationMessage(String.format("De quiz: %S is verwijderd",quiz.getQuizName()));

        setup();
    }

    public void doDashboard() {
            Main.getSceneManager().showCoordinatorDashboard();
            dbAccess.closeConnection();
    }

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
    }

    private void showInformationMessage(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(informationMessage);
        alert.show();
    }
}