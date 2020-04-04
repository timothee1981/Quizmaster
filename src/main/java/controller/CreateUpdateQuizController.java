package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Answer;
import model.Question;
import model.Quiz;
import view.Main;




public class CreateUpdateQuizController {
    Quiz quiz;

    @FXML
    private TextField quizNameTextField;

    @FXML
    private  TextField cesuurTextField;

    public void setup(Quiz quiz) {}

    public void doMenu() {
        Main.getSceneManager().showCoordinatorDashboard();
    }

    public void doCreateUpdateQuiz() {
        createQuiz();
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dbAccess);

        quizDAO.storeOne(quiz);
        dbAccess.closeConnection();
    }

    private void createQuiz() {


        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        String quizName = quizNameTextField.getText();
        double cesuur = Double.parseDouble(cesuurTextField.getText());

        quiz = new Quiz(quizName,cesuur);
       /* Answer answer = new Answer(goodAnswerTextField.getText(),question);
        Answer answer2 = new Answer(answer2TextField.getText(),question);
        Answer answer3 = new Answer(answer3TextField.getText(),question);
        Answer answer4 = new Answer(answer4TextField.getText(),question);
        question.voegAntwoordAanVraag(answer);
        question.voegAntwoordAanVraag(answer2);
        question.voegAntwoordAanVraag(answer3);
        question.voegAntwoordAanVraag(answer4);
        answers = question.getAnswers();*/



    }

}