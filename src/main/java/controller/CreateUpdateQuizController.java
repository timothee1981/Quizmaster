package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import view.Main;
import java.util.ArrayList;

public class CreateUpdateQuizController {

    DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
    CourseDAO courseDAO = new CourseDAO(dbAccess);
    QuestionDAO questionDAO = new QuestionDAO(dbAccess);
    AnswerDAO answerDAO = new AnswerDAO(dbAccess);
    QuizDAO quizDAO = new QuizDAO(dbAccess);
    Quiz quiz;

    @FXML
    public Label questionHeaderLabel;
    @FXML
    public Button questionCreateButton;
    @FXML
    public Button questionDeleteButton;
    @FXML
    public Button questionUpdateButton;
    @FXML
    private String labelvul;
    @FXML
    private String labelwijzig;
    @FXML
    private TextField quizNameTextField;
    @FXML
    public ComboBox cursusComboBox;
    @FXML
    private  TextField cesuurTextField;
    @FXML
    private Label titelLable, idLabel;
    @FXML
    private ListView<Question> questionList;
    @FXML
    public TextField courseIdTextField;

    public void setup(Quiz inputQuiz) {
        dbAccess.openConnection();

        // vul dropdown met alle cursussen
        fillCourseComboBox();

        // selecteer de juiste cursus
        SelectCourseOfQuiz(inputQuiz);

        // vul tekstvelden cursus (id-veld)
        fillFields(inputQuiz);

        // vul lijst met vragen behorende bij quiz
        fillQuestionList(inputQuiz);

        // check of het een nieuwe of een bestaande quiz is
        if(inputQuiz.getQuizId() == Question.DEFAULT_VRAAG) {
            // nieuwe quiz
            setupForNewQuiz();
        } else {
            // bestaande quiz
            setupForExistingQuiz(inputQuiz);
        }
        dbAccess.closeConnection();
    }

    private void setupForExistingQuiz(Quiz quiz) {
        labelwijzig = "Wijzig Quiz";
        titelLable.setText(labelwijzig);
        setTextFields(quiz);
    }

    private void setTextFields(Quiz quiz) {
        quizNameTextField.setText(quiz.getQuizName());
        cesuurTextField.setText(String.valueOf(quiz.getCesuur()));
        idLabel.setText(String.format("%d",quiz.getQuizId()));
    }

    private void setupForNewQuiz() {
        labelvul = "Vul quiz en bijbehorende vragen";
        titelLable.setText(labelvul);
        // je mag alleen vragen toevoegen aan een bestaande quiz
        hideQuestionFields();
    }

    private void fillQuestionList(Quiz quiz) {
        ArrayList<Question> getAllQuestionFromQuiz = questionDAO.getAllQuestionByQuizId(quiz.getQuizId());
        // vul vragenlijst
        questionList.getItems().clear();
        for(Question question: getAllQuestionFromQuiz){
            questionList.getItems().add(question);
        }
    }

    private void fillFields(Quiz quiz) {
        // vul Id
        courseIdTextField.setText(String.valueOf(quiz.getCourseId()));
    }

    private void SelectCourseOfQuiz(Quiz quiz) {
        // selecteer de juiste cursus
        Course selectedCourse = (Course)courseDAO.getOneById(quiz.getCourseId());
        cursusComboBox.setValue(selectedCourse);
    }

    private void fillCourseComboBox() {
        // vul dropdown met alle cursussen
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        ArrayList<Course> courseArrayList = courseDAO.getAll();
        for(Course course: courseArrayList) {
            cursusComboBox.getItems().add(course);
        }
    }

    private void hideQuestionFields() {
        questionList.setVisible(false);
        questionCreateButton.setVisible(false);
        questionDeleteButton.setVisible(false);
        questionHeaderLabel.setVisible(false);
        questionUpdateButton.setVisible(false);
    }

    public void doDashBoard(){
        Main.getSceneManager().showCoordinatorDashboard();
    }

    public void doManageQuiz(){
        Main.getSceneManager().showManageQuizScene();
    }

    public void doCreateUpdateQuiz() {

        // start fresh with empty quiz
        quiz = null;

        // get Id of course
        int courseId = getCourseIdFromPage();
        if(courseId == -1){
            return;
        }

        Course course = getCourseFromPage();
        if(course == null){
            return;
        }

        double cesuur = getCesuurFromPage();
        if(cesuur == 0.00){
            return;
        }

        String quizName = getQuizNameFromPage();
        if(quizName.equals("")){
            return;
        }



        // als je hier bent gekomen, is alle invoer correct :) yay
        updateQuizParameters(quizName, cesuur, course.getCursusId());

        if(quiz != null) {
            if (titelLable.getText().equals(labelvul)) {
                // = nieuwe quiz -> opslaan en message tonen
                StoreNewQuiz();

                UsefullStuff.showInformationMessage("De nieuwe quiz is opgeslagen\n Je gaat nu terug naar het dashboard");

                //navigeer naar dashboard terug
                Main.getSceneManager().showCoordinatorDashboard();

            } else if (titelLable.getText().equals(labelwijzig)) {
                // bestaande quiz - set Id -> opslaan en message tonen

                // set Quiz Id and save
                quiz.setQuizId(Integer.parseInt(idLabel.getText()));
                saveExistingQuiz();

                UsefullStuff.showInformationMessage("De quiz is geüpdated");
            }
        }
    }

    private void updateQuizParameters(String quizName, double cesuur, int courseId) {
        // alle invoer is correct -> Vul Quiz-object met juiste data (want globaal opgeslagen)
        quiz = new Quiz(quizName,cesuur);
        quiz.setCourseId(courseId);
    }

    private String getQuizNameFromPage() {
        String quizName = "";
        quizName = quizNameTextField.getText();
        if (quizName.isEmpty()) {
            UsefullStuff.showErrorMessage("Je moet een quiznaam invullen");
        }
        return quizName;
    }

    private double getCesuurFromPage() {
        double cesuur = 0.00;
        try {
            cesuur = Double.parseDouble(cesuurTextField.getText());
        } catch (NumberFormatException e){
            UsefullStuff.showErrorMessage("Cesuur moet een getal zijn");
        }
        return cesuur;
    }

    private Course getCourseFromPage() {
        Course course = null;
        course = (Course)cursusComboBox.getValue();
        if(course == null){
            UsefullStuff.showErrorMessage("Selecteer een cursus");
        }
        return course;
    }

    private int getCourseIdFromPage() {
        int courseId = -1;
        try {
            String courseidText = courseIdTextField.getText();
            courseId = Integer.parseInt(courseidText);
        } catch(Exception e){
           System.out.println(e.getMessage());
        }
        return courseId;
    }

    private void saveExistingQuiz() {
        // update the prepared quiz
        dbAccess.openConnection();
        quizDAO.updateQuiz(quiz);
        dbAccess.closeConnection();
    }

    private void StoreNewQuiz() {
        // save the prepared quiz
        dbAccess.openConnection();
        quizDAO.storeOne(quiz);
        dbAccess.closeConnection();
    }

    public void doCreateQuestion(){
        Question question = new Question();
        question.setQuiz(getCurrentQuiz());
        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doUpdateQuestion(){
        Question question = null;
        question = getQuestionFomPage();
        if(question == null){
            return;
        }

        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    private Question getQuestionFomPage() {
        Question question = null;
        question = questionList.getSelectionModel().getSelectedItem();
        if(question == null){
            UsefullStuff.showInformationMessage("Selecteer een vraag");
            return question;
        }
        question.setQuiz(getCurrentQuiz());
        return question;
    }

    public void doDeleteQuestion(){
        // get question information
        Question question = null;
        question = getQuestionFomPage();
        if(question == null){
            return;
        }

        // delete question
        dbAccess.openConnection();
        questionDAO.deleteQuestion(question);
        dbAccess.closeConnection();

        // refresh page
        setup(question.getQuiz());
    }

    private Quiz getCurrentQuiz() {
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        dbAccess.openConnection();
        Quiz currentQuiz = quizDAO.getOneById(Integer.parseInt(idLabel.getText()));
        dbAccess.closeConnection();
        return currentQuiz;
    }
}