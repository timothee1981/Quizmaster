package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Answer;
import model.Question;
import model.Quiz;
import view.Main;




public class CreateUpdateQuizController {
    Quiz quiz;
    private String labelvul;
    private String labelwijzig;

    @FXML
    private TextField quizNameTextField;

    @FXML
    private  TextField cesuurTextField;

    @FXML
    private Label titelLable, idLabel;

    @FXML
    private Button  menuButton1, returnCourseButton;

    public void setup(Quiz quiz) {
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