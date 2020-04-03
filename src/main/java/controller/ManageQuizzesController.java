package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Question;
import model.Quiz;
import org.w3c.dom.Text;
import view.Main;

import java.util.ArrayList;

public class ManageQuizzesController {
    QuizDAO quizDAO;

    @FXML
    private ListView quizList;

    public void setup() {
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        ArrayList<Quiz> getAllQuiz = quizDAO.getAll();
        for(Quiz quiz: getAllQuiz){
            quizList.getItems().add(quiz);
        }
    }

    public void doMenu(){}

    public void doCreateQuiz(){
        Quiz quiz = new Quiz();
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }

    public void doUpdateQuiz(){}

    public void doDeleteQuiz(){}
}