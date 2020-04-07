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


    @FXML
    ListView<Quiz> quizList;

    public void setup() {

        // clear quizList
        quizList.getItems().clear();

        // fill quizListwithAllQuizes
        fillQuizList();
    }

    private void fillQuizList() {
        ArrayList<Quiz> quizArrayList =  getAllQuizesFromDatabase();

        for(Quiz quiz: quizArrayList){
            quizList.getItems().add(quiz);
        }
    }

    private ArrayList<Quiz> getAllQuizesFromDatabase() {
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        dbAccess.openConnection();
        ArrayList<Quiz> getAllQuiz = quizDAO.getAll();
        dbAccess.closeConnection();
        return getAllQuiz;
    }

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
    }

    public void doQuiz() {
        Quiz quiz = null;
        quiz = (Quiz)quizList.getSelectionModel().getSelectedItem();
        if(quiz == null){
            UsefullStuff.showErrorMessage("Je moet een quiz aanklikken");
            return;
        }
        Main.getSceneManager().showFillOutQuiz(quiz);
    }
}
