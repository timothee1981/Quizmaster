package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import model.Course;
import model.Question;
import model.Quiz;
import org.w3c.dom.events.MouseEvent;
import view.Main;

import java.util.ArrayList;

public class CoordinatorDashboardController {

    private DBAccess dbAccess= new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());;
    private QuizDAO quizDAO = new QuizDAO(dbAccess);
    private QuestionDAO questionDAO = new QuestionDAO(dbAccess);
    private AnswerDAO answerDAO = new AnswerDAO(dbAccess);

    @FXML
    private ListView<Course> courseList;
    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private ListView<Question> questionList;



    public void setup() {

        dbAccess.openConnection();


       courseList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Course>() {
                    @Override
                    public void changed(ObservableValue<? extends Course> observableValue, Course oldCourse, Course newCourse) {
                        System.out.println("Geselecteerde cursus: " + observableValue + ", " + oldCourse + ", " + newCourse);
                    }
                });

        quizList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Quiz>() {
                    @Override
                    public void changed(ObservableValue<? extends Quiz> observableValue, Quiz oldQuiz, Quiz newQuiz) {
                        System.out.println("Geselecteerde quiz: " + observableValue + ", " + oldQuiz + ", " + newQuiz);
                        fillAnswerListview();
                    }



                });


        ArrayList<Quiz> getAllQuiz = quizDAO.getAll();
        for(Quiz quiz: getAllQuiz){
            quizList.getItems().add(quiz);

        }

        dbAccess.closeConnection();

    }

    private void fillAnswerListview() {
        dbAccess.openConnection();
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        Quiz quiz =  quizList.getSelectionModel().getSelectedItem();
        ArrayList<Question> getAllQuestion = questionDAO.getAllQuestionByQuizId(quiz.getQuizId());
        questionList.getItems().clear();
        for(Question question: getAllQuestion){

            questionList.getItems().add(question);
        }
        dbAccess.closeConnection();
    }

    public void doNewQuiz() {
        Main.getSceneManager().showCreateUpdateQuizScene(new Quiz());
    }

    public void doEditQuiz() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();

        if(quiz == null){
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een vraag aanklikken\n");
            foutmelding.show();
            return;

        }

        Main.getSceneManager().showCreateUpdateQuizScene(quiz);

    }

    public void doNewQuestion() {

        Main.getSceneManager().showCreateUpdateQuestionScene(new Question());
    }

    public void doEditQuestion() {
        Question question = questionList.getSelectionModel().getSelectedItem();

        if(question == null){
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een vraag aanklikken\n");
            foutmelding.show();
            return;

        }

        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }



}
