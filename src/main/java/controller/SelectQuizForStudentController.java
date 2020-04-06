package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Quiz;
import view.Main;

public class SelectQuizForStudentController {

    @FXML
    ListView<Quiz> quizList;

    public void setup() {}

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
    }

    public void doQuiz() {}
}
