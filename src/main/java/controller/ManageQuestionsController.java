package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Question;
import view.Main;

import java.util.ArrayList;

public class ManageQuestionsController {
    DBAccess dbAccess;
    QuestionDAO questionDAO;

    @FXML
    private ListView<Question> questionList;

    public void setup() {
            dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
            dbAccess.openConnection();
            this.questionDAO = new QuestionDAO(dbAccess);
            questionList.getItems().clear();
            ArrayList<Question> getAllQuestion = questionDAO.getAll();
            for(Question question: getAllQuestion){
                questionList.getItems().add(question);
            }

    }

    public void doMenu(){
        Main.getSceneManager().showCoordinatorDashboard();
        dbAccess.closeConnection();
    }

    public void doCreateQuestion(){
        Question question = new Question();
        Main.getSceneManager().showCreateUpdateQuestionScene(question);

    }

    public void doUpdateQuestion(){
        Question question = questionList.getSelectionModel().getSelectedItem();

        if(question == null){
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een vraag aanklikken\n");
            foutmelding.show();
            return;

        }

        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doDeleteQuestion(){
        dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        AnswerDAO answerDAO = new AnswerDAO(dbAccess);
        Question question = questionList.getSelectionModel().getSelectedItem();
        int questionId = question.getQuestionId();
        answerDAO.deleteAnswerfromQuestion(questionId);

        if(question == null) {

            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een customer aanklikken\n");
            foutmelding.show();
            return;

        }

        questionDAO.deleteQuestion(question);
        setup();


    }
}
