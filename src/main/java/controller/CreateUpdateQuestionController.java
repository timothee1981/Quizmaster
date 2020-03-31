package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Answer;
import model.Question;
import view.Main;

public class CreateUpdateQuestionController {

    private Question question;

    @FXML
    private TextField niewevraag;

    @FXML
    private  TextField goedeAntwoord;

    public void setup(Question question) {}

    public void doMenu() {
        Main.getSceneManager().showCoordinatorDashboard();
    }

    public void doCreateUpdateQuestion() {}

    private void createQuestion() {


        String questionString = niewevraag.getText();
        String correctAnswer = goedeAntwoord.getText();

       Question question = new Question(1,questionString,new Answer(1,correctAnswer));

    }
}