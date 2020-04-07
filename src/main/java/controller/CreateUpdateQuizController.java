package controller;

import com.mysql.cj.xdevapi.DbDoc;
import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import view.Main;

import java.util.ArrayList;
import java.util.List;


public class CreateUpdateQuizController {

    @FXML
    public Label questionHeaderLabel;
    @FXML
    public Button questionCreateButton;
    @FXML
    public Button questionDeleteButton;
    @FXML
    public Button questionUpdateButton;

    DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
    QuestionDAO questionDAO = new QuestionDAO(dbAccess);
    AnswerDAO answerDAO = new AnswerDAO(dbAccess);
    QuizDAO quizDAO = new QuizDAO(dbAccess);
    Quiz quiz;
    private String labelvul;
    private String labelwijzig;

    @FXML
    private TextField quizNameTextField;

    @FXML
    public ComboBox cursusComboBox;

    @FXML
    private  TextField questionTextField;

    @FXML
    private  TextField cesuurTextField;

    @FXML
    private Label titelLable, idLabel;

    @FXML
    private Button  menuButton1, returnCourseButton;

    @FXML
    private ListView<Question> questionList;

    @FXML
    public TextField courseIdTextField;

    public void setup(Quiz quiz) {
        dbAccess.openConnection();

        // vul cursus-combobox met alle cursussen
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        ArrayList<Course> courseArrayList = courseDAO.getAll();
        for(Course course: courseArrayList) {
            cursusComboBox.getItems().add(course);
        }
        // selecteer de juiste cursus
        Course selectedCourse = (Course)courseDAO.getOneById(quiz.getCourseId());
        cursusComboBox.setValue(selectedCourse);

        // vul id van cursus in
        courseIdTextField.setText(String.valueOf(quiz.getCourseId()));

        ArrayList<Question> getAllQuestionFromQuiz = questionDAO.getAllQuestionByQuizId(quiz.getQuizId());

        // vul vragenlijst
        questionList.getItems().clear();
        for(Question question: getAllQuestionFromQuiz){
           questionList.getItems().add(question);
        }

        if(quiz.getQuizId() == Question.DEFAULT_VRAAG) {
            // nieuwe quiz
            labelvul = "Vul quiz en bijbehorende vragen";
            titelLable.setText(labelvul);
            // todo: verberg het vragen-gedeelte -> na opslaan wordt er terug-genavigeerd naar het dashboard
            // je mag alleen vragen toevoegen aan een bestaande quiz
            hideQuestionFields();
            
        } else {
            // bestaande quiz
            labelwijzig = "Wijzig Quiz";
            titelLable.setText(labelwijzig);

            quizNameTextField.setText(quiz.getQuizName());
            cesuurTextField.setText(String.valueOf(quiz.getCesuur()));
            int quizId = quiz.getQuizId();
            String quizIDString = String.format("%d",quizId);
            idLabel.setText(quizIDString);
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

        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;

        // get Id of course
        String courseIdString = courseIdTextField.getText();
        int courseId = 0;
        try {
            courseId = Integer.parseInt(courseIdTextField.getText());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        // get cesuur and validate:
        double cesuur;
        try {
            cesuur = Double.parseDouble(cesuurTextField.getText());
        } catch (NumberFormatException e){
            warningText.append("Cesuur moet een getal zijn\n");
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            quiz = null;
            return;
        }

        String quizName = quizNameTextField.getText();
        // check if input = correct
        if (quizName.isEmpty() || cesuurTextField.getText().isEmpty()) {
            warningText.append("Je moet een quiznaam invullen en een cesuur\n");
            correcteInvoer = false;
        }
        if (!correcteInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            quiz = null;
            return;
        } else {
            // alle invoer is correct ->
            quiz = new Quiz(quizName,cesuur);
            quiz.setCourseId(courseId);
        }



        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        QuizDAO quizDAO = new QuizDAO(dbAccess);

        if(quiz != null) {
            if (titelLable.getText().equals(labelvul)) {
                // = nieuwe quiz
                quizDAO.storeOne(quiz);
                System.out.println("Er wordt een nieuwe quiz opgeslagen\n Je gaat nu terug naar het dashboard");

                //navigeer naar dashboard terug
                Main.getSceneManager().showCoordinatorDashboard();

            } else if (titelLable.getText().equals(labelwijzig)) {
                int id = Integer.valueOf(idLabel.getText());
                quiz.setQuizId(id);
                quizDAO.updateQuiz(quiz);
                System.out.println("De quiz wordt aangepast");
            }
        }
        dbAccess.closeConnection();

        // toon user melding dat er een actie is gebeurd
        Alert melding = new Alert(Alert.AlertType.INFORMATION);
        melding.setContentText("De quiz is geüpdated of aangepast\n");
        melding.show();

    }

    public void doCreateQuestion(){
        Question question = new Question();
        Main.getSceneManager().showCreateUpdateQuestionScene(question);

    }

    public void doUpdateQuestion(){
        Question question = questionList.getSelectionModel().getSelectedItem();

        if(question == null){
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een vraag aanklikken\n");
            foutmelding.show();
            return;

        }

        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doDeleteQuestion(){
        dbAccess.openConnection();
        quizDAO = new QuizDAO(dbAccess);
        int quizId = Integer.parseInt(idLabel.getText());
        quiz = quizDAO.getOneById(quizId);
        Question question = questionList.getSelectionModel().getSelectedItem();
        int questionId = question.getQuestionId();
        answerDAO.deleteAnswerfromQuestion(questionId);
        if(question == null) {

            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText("Je moet een vraag aanklikken\n");
            foutmelding.show();
            return;
        }
        questionDAO.deleteQuestion(question);
        setup(quiz);
    }

}