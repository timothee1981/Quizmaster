package controller;

import database.mysql.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
    private CourseDAO courseDAO = new CourseDAO(dbAccess);
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

        ArrayList<Course> courses = courseDAO.getAll();
        for(Course course: courses){
            courseList.getItems().add(course);
        }

       courseList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Course>() {
                    @Override
                    public void changed(ObservableValue<? extends Course> observableValue, Course oldCourse, Course newCourse) {
                        quizList.getItems().clear();
                        quizList.getItems().clear();
                        fillQuizListview();
                    }
                });

        quizList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Quiz>() {
                    @Override
                    public void changed(ObservableValue<? extends Quiz> observableValue, Quiz oldQuiz, Quiz newQuiz) {
                        questionList.getItems().clear();
                        fillAnswerListview();
                    }
                });
        dbAccess.closeConnection();
    }

    private void fillQuizListview() {
        dbAccess.openConnection();
        Course course = courseList.getSelectionModel().getSelectedItem();
        if(course == null){
            return;
        }

        ArrayList<Quiz> getAllQuiz = quizDAO.getAllQuizbyCursusId(course.getCursusId());
        for (Quiz quiz : getAllQuiz) {
            quizList.getItems().add(quiz);
        }
    }

    private void fillAnswerListview() {
        dbAccess.openConnection();
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        Quiz quiz =  quizList.getSelectionModel().getSelectedItem();
        if(quiz == null){
            return;
        }
        ArrayList<Question> getAllQuestion = questionDAO.getAllQuestionByQuizId(quiz.getQuizId());
        questionList.getItems().clear();
        for(Question question: getAllQuestion){

            questionList.getItems().add(question);
        }
        dbAccess.closeConnection();
    }

    public void doNewQuiz() {
        // get selected course -> hier wordt de quiz aan gekoppeld
        Course course = null;
        course = courseList.getSelectionModel().getSelectedItem();
        if(course == null){
            // geen cursus geselecteerd
            showErrorMessage("Selecteer een cursus waar je de quiz aan wilt toevoegen");
            return;
        }
        // maak quiz aan en koppel de quiz aan cursus
        Quiz quiz = new Quiz();
        quiz.setCourseId(course.getCursusId());

        //navigeer naar scene:
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
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

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
    }

}
