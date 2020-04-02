package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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



    public void setup(Question question) {}

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


        if (question != null) {

            questionDAO.storeOne(question);

            for(Answer answer1: answers)
            answerDAO.storeOne(answer1);



        }else
            System.out.println("geen klant");



        //answerDAO.storeNewAnswer(question.getCorrectAnswer().getAnswerId(),question.getCorrectAnswer().getAnswer(),question.getCorrectAnswer().getQuestion());
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

