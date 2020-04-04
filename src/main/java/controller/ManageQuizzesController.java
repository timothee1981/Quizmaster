package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Question;
import model.Quiz;
import org.w3c.dom.Text;
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
        quizList.getItems().clear();
        ArrayList<Quiz> getAllQuiz = quizDAO.getAll();
        for(Quiz quiz: getAllQuiz){
            quizList.getItems().add(quiz);
        }
    }

    public void doMenu(){
        Main.getSceneManager().showCoordinatorDashboard();
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
   //   Question questioDAO = new QuestionDAO(dbAccess);
        Quiz quiz = (Quiz) quizList.getSelectionModel().getSelectedItem();
        int quizId = quiz.getQuizId();
     //   questioDAO.deleteAnswerfromQuestion(questionId);

        if(quiz == null) {

            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een customer aanklikken\n");
            foutmelding.show();
            return;

        }

        quizDAO.deleteQuizBiId(quizId);
        setup();
    }
}