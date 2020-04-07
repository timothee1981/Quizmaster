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

            // vul antwoorden in:
            goodAnswerTextField.setText(question.getAnswers().get(0).toString());
            answer2TextField.setText(question.getAnswers().get(1).toString());
            answer3TextField.setText(question.getAnswers().get(2).toString());
            answer4TextField.setText(question.getAnswers().get(3).toString());
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
        createAnswerBijQuestion(question);

        // due to global datastorage - first clear question/answer etc.


        // maak database-connectie
        dbAccess.openConnection();
        questionDAO = new QuestionDAO(dbAccess);
        answerDAO = new AnswerDAO(dbAccess);
        //check als er een nieuwe vraag moet komen of als een bestaande vraag moet gewijzigd worden
        if (titelLabel.getText().equals(labelvul)) {//als titel op nieuwe is-----> nieuwe vraag
            //check als combobox waarde heeft
            if (!(quizComboBox.getValue() == null)) {
                //als combobox waarde creer vraag

                // vul quiz in die bij vraag hoort
                question.setQuiz(quizComboBox.getValue());
                // check of je vraag een waarde heeft:
                if(!(question.getQuestion().isEmpty())) {
                    // vraag heeft een waarde:


                   // createAnswerBijQuestion(question);
                   // answers = question.getAnswers();

                    // bepaal goede antwoord
                    Answer correctAnswer = new Answer(goodAnswerTextField.getText(), question);
                    question.setCorrectAnswer(correctAnswer);

                    // save question
                    questionDAO.storeOne(question);
                    // get all answers of question from database:
                    AnswerDAO answerDAO = new AnswerDAO(dbAccess);
                    ArrayList<Answer> answerArrayList = answerDAO.getAnswersByQuestionId(question.getQuestionId());
                    int correctAnswerId = 0;
                    for(Answer answer: answerArrayList){
                        if(correctAnswer.getAnswer().equals(answer.getAnswer())){
                            correctAnswerId = answer.getAnswerId();
                        }
                    }
                    //check if correct question == that question -> get externalId -> pass this id to updateGoedeAntwoordId method
                    questionDAO.updateGoedAntwoodId(correctAnswerId, question.getQuestionId());

                    System.out.println(("nieuwe vraag opgeslagen"));
                }else{
                    // toon waarschuwing, je moet nog een vraag invullen
                    warningTextNoQuestion();
                    return;
                }
            } else {
                // toon melding: je moet nog een quiz selecteren
                noQuizWarning();
                return;
            }
        } else if (titelLabel.getText().equals(labelwijzig)) { //dit is bij wijziging van een vraag
            int id = Integer.valueOf(idQuestion.getText());
            question.setQuestionId(id);
            updateQuestionById(question);
            System.out.println(("vraag gewijzigd"));
        }
        dbAccess.closeConnection();
        // ga terug naar dashboard
        Main.getSceneManager().showCoordinatorDashboard();
    }

    private void createAnswerBijQuestion(Question   question) {
        Answer answer = new Answer(goodAnswerTextField.getText(), question);
        Answer answer2 = new Answer(answer2TextField.getText(), question);
        Answer answer3 = new Answer(answer3TextField.getText(), question);
        Answer answer4 = new Answer(answer4TextField.getText(), question);
        question.voegAntwoordAanVraag(answer);
        question.voegAntwoordAanVraag(answer2);
        question.voegAntwoordAanVraag(answer3);
        question.voegAntwoordAanVraag(answer4);
        answers = question.getAnswers();
        for(Answer answer1: answers){
           boolean hasAnswer =  validateAnswerString(answer1.getAnswer());
            if(!hasAnswer){
                allAnswerFilledWarning();
                return;

            }
        }


    }

    private boolean validateAnswerString(String rawAnswerString) {
        if(rawAnswerString.equals("") || rawAnswerString == null){
            return false;
        } else{
            return true;
        }
    }

    private void noQuizWarning() {
        warningText.append("Je moet een quiz kiezen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
    }

    private void allAnswerFilledWarning() {
        warningText.append("Je moet een antwoord invullen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
        return;

    }


    private void warningTextNoQuestion() {
        warningText.append("Je moet een goede vraag invullen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
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
        question = new Question(vraagTextField.getText());

        dbAccess.closeConnection();

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


