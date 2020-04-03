package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Answer;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;

public class CreateUpdateQuestionController {

    private Question question;
    private ArrayList<Question> questions;
    private ArrayList<Answer> answers;
    private String labelvul;
    private String labelwijzig;

    @FXML
    private  Label idQuestion, idGoodAnswer,idAnswer1,idAnswer2,idAnswer3,idAnswer4;

    @FXML
    private Label titelLabel;

    @FXML
    private TextField vraagTextField;

    @FXML
    private  TextField goodAnswerTextField;

    @FXML
    private  TextField answer2TextField;

    @FXML
    private TextField answer3TextField;

    @FXML
    private TextField answer4TextField;



    public void setup(Question question) {
        ArrayList<Answer> answers = new ArrayList<>();

        if (question.getQuestionId() == Question.DEFAULT_VRAAG) {
            labelvul = "Vull Vraag en antwoord";
            titelLabel.setText(labelvul);
        } else {

            labelwijzig = "Wijzig Vraag";
            titelLabel.setText(labelwijzig);
            vraagTextField.setText(question.getQuestion());
            int questionId = question.getQuestionId();
            String questionIdString = String.format("%d",questionId);
            idQuestion.setText(questionIdString);
            answers = returnArrayAnswers(question.getQuestionId());

            goodAnswerTextField.setText(answers.get(0).getAnswer());
            answer2TextField.setText(answers.get(1).getAnswer());
            answer3TextField.setText(answers.get(2).getAnswer());
            answer4TextField.setText(answers.get(3).getAnswer());



        }
    }



    public ArrayList<Answer> returnArrayAnswers(int questionId){
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        AnswerDAO answerDAO = new AnswerDAO(dbAccess);
        ArrayList<Answer> answers = new ArrayList<>();
        answers = answerDAO.getAnswersByQuestionId(questionId);
        return answers;

    }

    public void doMenu() {
        Main.getSceneManager().showCoordinatorDashboard();

    }

    @FXML
    public void doCreateUpdateQuestion(ActionEvent actionEvent) {
        createQuestion();
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        AnswerDAO answerDAO = new AnswerDAO(dbAccess);


            // roep save-methode aan
        if(titelLabel.getText().equals(labelvul)) {
            questionDAO.storeOne(question);
            for (Answer answer1 : answers) {
                answerDAO.storeOne(answer1);
                System.out.println("opgeslagen");
            }
        }else if(titelLabel.getText().equals(labelwijzig)) {
            int id = Integer.valueOf(idQuestion.getText());
            question.setQuestionId(id);
            updateQuestionById(question);
            System.out.println(("gewijzigd"));
        }



            dbAccess.closeConnection();
    }
    private void updateQuestionById(Question question) {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        // roep save-methode aan
        questionDAO.updateQuestion(question);
        // sluit database connectie
        dbAccess.closeConnection();
    }





    private void createQuestion() {


        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;

        question = new Question(vraagTextField.getText());
        Answer answer = new Answer(goodAnswerTextField.getText(),question);
        Answer answer2 = new Answer(answer2TextField.getText(),question);
        Answer answer3 = new Answer(answer3TextField.getText(),question);
        Answer answer4 = new Answer(answer4TextField.getText(),question);
        question.voegAntwoordAanVraag(answer);
        question.voegAntwoordAanVraag(answer2);
        question.voegAntwoordAanVraag(answer3);
        question.voegAntwoordAanVraag(answer4);
        answers = question.getAnswers();



        }



    }


