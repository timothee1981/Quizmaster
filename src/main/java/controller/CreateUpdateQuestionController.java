package controller;

import database.mysql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Answer;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;

public class CreateUpdateQuestionController {
    private DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private Question question;
    private ArrayList<Answer> answers;
    private String labelvul;
    private String labelwijzig;
    private StringBuilder warningText = new StringBuilder();

    @FXML
    private  Label idQuestion, idGoodAnswer,idAnswer2,idAnswer3,idAnswer4, idQuiz;

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

    @FXML
    private ComboBox<Quiz> quizComboBox;

    public void setup(Question question) {
        dbAccess.openConnection();
        answerDAO = new AnswerDAO(dbAccess);

        // vul dropdown met alle quizes
        fillQuizDropdown();

        // vul bijbehorende quiz in
        setQuizDropdownOfUser(question.getQuiz());

        if (question.getQuestionId() == Question.DEFAULT_VRAAG) {
            // nieuwe vraag
            labelvul = "Vul Vraag en antwoord";
            titelLabel.setText(labelvul);
        } else {
            // bestaande vraaag
            labelwijzig = "Wijzig Vraag";
            titelLabel.setText(labelwijzig);

            // vul vraag-textveld in:
            vraagTextField.setText(question.getQuestion());
            int questionId = question.getQuestionId();
            String questionIdString = String.format("%d",questionId);
            idQuestion.setText(questionIdString);

            // haal antwoorden op
            answers = returnArrayAnswers(questionId);

            // vul antwoorden in:
            goodAnswerTextField.setText(answers.get(0).getAnswer());
            answer2TextField.setText(answers.get(1).getAnswer());
            answer3TextField.setText(answers.get(2).getAnswer());
            answer4TextField.setText(answers.get(3).getAnswer());
        }
    }

    private void setQuizDropdownOfUser(Quiz quiz) {
        quizComboBox.setValue(quiz);
    }

    public ArrayList<Answer> returnArrayAnswers(int questionId){
        dbAccess.openConnection();
        AnswerDAO answerDAO = new AnswerDAO(dbAccess);
        ArrayList<Answer> answers;
        answers = answerDAO.getAnswersByQuestionId(questionId);
        return answers;
    }

    public void doMenu() {
        Main.getSceneManager().showCoordinatorDashboard();
    }

    @FXML
    public void doCreateUpdateQuestion(ActionEvent actionEvent) {
        createQuestion();
        // maak database-connectie
        dbAccess.openConnection();
        questionDAO = new QuestionDAO(dbAccess);
        answerDAO = new AnswerDAO(dbAccess);

            // roep save-methode aan
        if (titelLabel.getText().equals(labelvul)) {
            if (!(quizComboBox.getValue() == null)) {
                question.setQuiz(quizComboBox.getValue());
                if(!(question.getQuestion().isEmpty())) {
                    questionDAO.storeOne(question);
                    for(Answer answer: answers){
                        answerDAO.storeOne(answer);
                    }
                    questionDAO.updateGoedAntwoodId(answers.get(0).getAnswerId(), question.getQuestionId());
                }else{
                    warningTextNoQuestion();
                }
            } else {
                noQuizWarning();
            }
        } else if (titelLabel.getText().equals(labelwijzig)) { //dit is bij wijziging van een vraag
            int id = Integer.valueOf(idQuestion.getText());
            question.setQuestionId(id);
            updateQuestionById(question);
            System.out.println(("gewijzigd"));
        }
        dbAccess.closeConnection();
    }

    private void noQuizWarning() {
        warningText.append("Je moet een quiz kiezen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
        question = null;
    }

    private void allAnswerFilledWarning() {
        warningText.append("Je moet een antwoord invullen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
        return;

    }


    private void warningTextNoQuestion() {
        warningText.append("Je moet een vraag invullen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
        return;
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

        dbAccess.openConnection();
        answerDAO = new AnswerDAO(dbAccess);
        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;

        question = new Question(vraagTextField.getText());

        Answer answer = new Answer(goodAnswerTextField.getText(), question);
        Answer answer2 = new Answer(answer2TextField.getText(), question);
        Answer answer3 = new Answer(answer3TextField.getText(), question);
        Answer answer4 = new Answer(answer4TextField.getText(), question);
        question.voegAntwoordAanVraag(answer);
        question.voegAntwoordAanVraag(answer2);
        question.voegAntwoordAanVraag(answer3);
        question.voegAntwoordAanVraag(answer4);
        answers = question.getAnswers();

        dbAccess.closeConnection();

        String actualAnswer = "";
        int actualIndex = 0;
    }

    private ArrayList<Quiz> getAllQuizItems() {

        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        ArrayList<Quiz> quizItems = quizDAO.getAll();
        dbAccess.closeConnection();

        return quizItems;
    }

    private void fillQuizDropdown() {
        ArrayList<Quiz> quizList = getAllQuizItems();
        for(Quiz quiz: quizList){
            quizComboBox.getItems().add(quiz);
        }
    }
}


