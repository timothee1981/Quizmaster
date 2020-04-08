package controller;

import CouchDBControllers.QuestionCouchDBController;
import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Answer;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.ArrayList;

public class CreateUpdateQuestionController {
    private DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
    private Question question;
    private ArrayList<Answer> answers;
    private String labelvul;
    private String labelwijzig;
    private StringBuilder warningText = new StringBuilder();


    @FXML
    private  Label idQuestion;
    @FXML
    private Label idGoodAnswer;
    @FXML
    private Label idAnswer2;
    @FXML
    private Label idAnswer3;
    @FXML
    private Label idAnswer4;

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
            setQuestionTextField(question);
            // vul antwoorden in:
            setAnswersField(question.getAnswers());
           //zet idlabel op waarde van id aantwoorden
            setAnswersLableId(question.getAnswers());
        }
    }

    //methode om question textfield te vullen
    private void setQuestionTextField(Question question) {
        vraagTextField.setText(question.getQuestion());
        int questionId = question.getQuestionId();
        String questionIdString = String.format("%d",questionId);
        idQuestion.setText(questionIdString);
    }

    // methoden om Antwoord field te vullen
    private void setAnswersField(ArrayList<Answer> answers) {
        goodAnswerTextField.setText(answers.get(0).toString());
        answer2TextField.setText(answers.get(1).toString());
        answer3TextField.setText(answers.get(2).toString());
        answer4TextField.setText(answers.get(3).toString());
    }

    //methode om waarde te geven aan id antwoord label

    private void setAnswersLableId(ArrayList<Answer> answers) {
        String goodAnswerId = String.format("%d",answers.get(0).getAnswerId());
        String answer2 = String.format("%d",answers.get(1).getAnswerId());
        String answer3 = String.format("%d",answers.get(2).getAnswerId());
        String answer4 = String.format("%d",answers.get(3).getAnswerId());
        idGoodAnswer.setText(goodAnswerId);
        idAnswer2.setText(answer2);
        idAnswer3.setText(answer3);
        idAnswer4.setText(answer4);
    }

    private void setQuizDropdownOfUser(Quiz quiz) {
        quizComboBox.setValue(quiz);
    }

    public void doMenu() {
        Main.getSceneManager().showCoordinatorDashboard();
    }

    @FXML
    public void doCreateUpdateQuestion() {
       createQuestion();
        dbAccess.openConnection();                      // maak database-connectie
        //check als er een nieuwe vraag moet komen of als een bestaande vraag moet gewijzigd worden
        if (titelLabel.getText().equals(labelvul)) {    //als titel op nieuwe is-----> nieuwe vraag
            checkIfQuestionFilled(question);//check als combobox waarde heeft
            if(question == null){
                return;
            }
        } else if (titelLabel.getText().equals(labelwijzig)) {  //dit is bij wijziging van een vraag als titel op wijzig is
            if(question == null){
                return;
            }
            int questionId = Integer.parseInt(idQuestion.getText());
            updateQuestion(questionId);
        }
        dbAccess.closeConnection();                           //sluit database
        Main.getSceneManager().showCoordinatorDashboard();   // ga terug naar dashboard
    }

    private void updateQuestion(int questionId) {
        question.setQuestionId(questionId);
        updateQuestionById(question);
        updateAnswersFromQuestion(question);
        UsefullStuff.showInformationMessage("Vraag gewijzigd");
    }

    private void checkIfQuestionFilled(Question question) {
        if(question != null) {
            if (!(quizComboBox.getValue() == null)) {
                //als combobox waarde creer vraag
                vullVraagBijQuizz(quizComboBox.getValue()); // vul quiz in die bij vraag hoort
            } else {
                // toon melding: je moet nog een quiz selecteren
                noQuizWarning();
                return;
            }
        }else {
            return;
        }
    }

    //vull vraag die bij quiz hoort
    private void vullVraagBijQuizz(Quiz value) {

        question.setQuiz(value);
        // check of je vraag een waarde heeft:
        if (!(question.getQuestion().isEmpty())) {
            // vraag heeft een waarde:
            // bepaal goede antwoord

            Answer correctAnswer = new Answer(goodAnswerTextField.getText(), question);
            question.setCorrectAnswer(correctAnswer);
            QuestionDAO questionDAO = new QuestionDAO(dbAccess);
            questionDAO.storeOne(question);     // save question
            AnswerDAO answerDAO = new AnswerDAO(dbAccess);           // get all answers of question from database:
            ArrayList<Answer> answerArrayList = answerDAO.getAnswersByQuestionId(question.getQuestionId());
            getCorrectAnswer(answerArrayList, correctAnswer);
        } else {
            // toon waarschuwing, je moet nog een vraag invullen
            warningTextNoQuestion();
        }
    }

    private void getCorrectAnswer(ArrayList<Answer> answerArrayList, Answer correctAnswer) {
        dbAccess.openConnection();
        QuestionDAO questionDAO= new QuestionDAO(dbAccess);
        int correctAnswerId = 0;
        for (Answer answer : answerArrayList) {
            if (correctAnswer.getAnswer().equals(answer.getAnswer())) {
                correctAnswerId = answer.getAnswerId();
            }
        }
        questionDAO.updateGoedAntwoodId(correctAnswerId, question.getQuestionId());

        System.out.println(("nieuwe vraag opgeslagen"));
        dbAccess.closeConnection();
    }

    private void updateAnswersFromQuestion(Question question) {
        //haal aantwoorden uit vragen en zet ze in een array
        AnswerDAO answerDOA = new AnswerDAO(dbAccess);
        answers = question.getAnswers();

        answers.get(0).setAnswerId(Integer.parseInt(idGoodAnswer.getText()));
        answers.get(1).setAnswerId(Integer.parseInt(idAnswer2.getText()));
        answers.get(2).setAnswerId(Integer.parseInt(idAnswer3.getText()));
        answers.get(3).setAnswerId(Integer.parseInt(idAnswer4.getText()));



        //update voor iedere vraag hun String
        for(Answer answer: answers){
            answerDOA.updateAnswer(answer);
        }


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

    }

    private boolean validateAnswerString(String rawAnswerString) {
        return !rawAnswerString.equals("") && rawAnswerString != null;
    }

    private void noQuizWarning() {
        warningText.append("Je moet een quiz kiezen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
    }

    private void allAnswerFilledWarning() {
        StringBuilder warningText = new StringBuilder();
        warningText.append("Je moet een antwoord invullen\n");
        Alert foutmelding = new Alert(Alert.AlertType.ERROR);
        foutmelding.setContentText(warningText.toString());
        foutmelding.show();
        warningText.delete(0,warningText.length());

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

        // validate question-input
        String questionInput = "";
        questionInput = vraagTextField.getText();
        if(questionInput.equals("")){
            question = null;
            UsefullStuff.showErrorMessage("Vul een vraag in");
        }

        // als het fout is, zet quiz op null

        question = new Question(vraagTextField.getText());
        createAnswerBijQuestion(question);
        boolean hasAnswer = true;
        for(Answer answer1: answers){
            hasAnswer =  validateAnswerString(answer1.getAnswer());
        }
        if(!hasAnswer){
            allAnswerFilledWarning();
            question = null;
        }

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


