package controller;

import com.mysql.cj.xdevapi.DbDoc;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Answer;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;


public class CreateUpdateQuizController {
    Quiz quiz;
    private String labelvul;
    private String labelwijzig;

    @FXML
    private TextField quizNameTextField;

    @FXML
    private  TextField questionTextField;

    @FXML
    private  TextField cesuurTextField;

    @FXML
    private Label titelLable, idLabel;

    @FXML
    private Button  menuButton1, returnCourseButton;

    @FXML
    private ListView<Question> questionList;

    public void setup(Quiz quiz) {
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        ArrayList<Question> getAllQuestionFromQuiz = questionDAO.getAllQuestionByQuizId(quiz.getQuizId());
        for(Question question: getAllQuestionFromQuiz){
           questionList.getItems().add(question);
        }

        if (quiz.getQuizId() == Question.DEFAULT_VRAAG) {
            labelvul = "Vull quiz en bijhorende vragen";
            titelLable.setText(labelvul);
        } else {

            labelwijzig = "Wijzig Quiz";
            titelLable.setText(labelwijzig);
            quizNameTextField.setText(quiz.getQuizName());
            cesuurTextField.setText(String.valueOf(quiz.getCesuur()));
            int quizId = quiz.getQuizId();
            String quizIDString = String.format("%d",quizId);
            idLabel.setText(quizIDString);
        }
    }



    public void doDashBoard(){

        Main.getSceneManager().showCoordinatorDashboard();
    }

    public void doManageQuiz(){
        Main.getSceneManager().showManageQuizScene();
    }

    public void doCreateUpdateQuiz() {
        createQuiz();
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dbAccess);

        if(titelLable.getText().equals(labelvul)){
            quizDAO.storeOne(quiz);
            System.out.println("Toegevoegd");
            /*for (Answer answer1 : answers) {
                answerDAO.storeOne(answer1);
                System.out.println("opgeslagen");*/
           // }
        }else if(titelLable.getText().equals(labelwijzig)){
            int id = Integer.valueOf(idLabel.getText());
            quiz.setQuizId(id);
            quizDAO.updateQuiz(quiz);
            System.out.println(("gewijzigd"));
        }


        dbAccess.closeConnection();
    }


    private void createQuiz() {


        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        String quizName = quizNameTextField.getText();
        double cesuur = Double.parseDouble(cesuurTextField.getText());

        quiz = new Quiz(quizName,cesuur);

    }

    public void doCreateQuestion(){
        Question question = new Question();
        Main.getSceneManager().showCreateUpdateQuestionScene(question);

    }

}