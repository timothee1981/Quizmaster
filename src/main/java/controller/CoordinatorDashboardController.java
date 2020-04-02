package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Course;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.ArrayList;

public class CoordinatorDashboardController {
    private QuestionDAO questionDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Course> courseList;
    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private ListView<Question> questionList;

    public void setup() {
        dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        this.questionDAO = new QuestionDAO(dbAccess);
        ArrayList<Question> getAllQuestion = questionDAO.getAll();
        for(Question question: getAllQuestion){
            questionList.getItems().add(question);
        }
        dbAccess.closeConnection();

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
                    }
                });
    }

    public void doNewQuiz() {
        Main.getSceneManager().showCreateUpdateQuizScene(new Quiz());
    }

    public void doEditQuiz() { }

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
