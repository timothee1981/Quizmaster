package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Answer;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.ArrayList;

public class FillOutQuizController {
    private DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private Quiz quiz;
    private QuizDAO quizDAO;
    private int questionCount = 0;



    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;

    @FXML
    private Label  quizIdLabel,questionIdLabel;

    public void setup(Quiz quiz) {
        dbAccess.openConnection();
        this.questionDAO = new QuestionDAO(dbAccess);
        this.answerDAO = new AnswerDAO(dbAccess);
        //check even welke quiz het om gaat

        quizIdLabel.setText(String.valueOf(quiz.getQuizId()));
        //haal antwoord bij quiz
        ArrayList<Question> getAllQuestionFromQuiz = questionDAO.getAllQuestionByQuizId(quiz.getQuizId());


        // haal vraag uit quiz afhankelijk van de vraag teller
        showQuestionOnCount(getAllQuestionFromQuiz,questionCount);
        dbAccess.closeConnection();



    }

    private void showQuestionOnCount(ArrayList<Question> getAllQuestion,  int questionCount) {
        dbAccess.openConnection();
        this.questionDAO = new QuestionDAO(dbAccess);
        this.answerDAO = new AnswerDAO(dbAccess);


        //welke vraag willen we
        Question question = getAllQuestion.get(questionCount);

        //check welke vraag id we hebben

        questionIdLabel.setText(String.valueOf(question.getQuestionId()));
        questionArea.clear();
        //zet eerste vraag vvan quiz
        questionArea.appendText(question.getQuestion() + "\n");
        ArrayList<Answer> answers = answerDAO.getAnswersByQuestionId(question.getQuestionId());
        //en full question area met antwoorden eerste vraag
        fillanswerArea(answers);
        dbAccess.closeConnection();
    }

    private void fillanswerArea(ArrayList<Answer> answers) {
        dbAccess.openConnection();
        answerDAO = new AnswerDAO(dbAccess);
        int count = 1;
        for(int arrayteller = 0; arrayteller< answers.size();arrayteller++) {
            questionArea.appendText("vraag " + count++ + ": " + answers.get(arrayteller).getAnswer() + "\n");
        }

        dbAccess.closeConnection();
    }

    public void doRegisterA() {
        //kies aantwoord A ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doRegisterB() {
        //kies aantwoord B ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doRegisterC() {
        //kies aantwoord C ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doRegisterD() {
        //kies aantwoord D ....sla op in String aantwoord gebruiker
        //methode van quizrestult:
        // vergelijke antwoord met aantwoord array, aantwoorden juist ++ als goed is
    }

    public void doNextQuestion() {
        //ga na volgend vraag in de quiz....setup methode aanroep
        dbAccess.openConnection();
        quizDAO = new QuizDAO(dbAccess);
        int quizId = Integer.parseInt(quizIdLabel.getText());
        quiz = quizDAO.getOneById(quizId);
        //zverandert tekst titel lable
        titleLabel.setText("Vraag " + (++questionCount));
        setup(quiz);
        dbAccess.closeConnection();

    }

    public void doPreviousQuestion() {


        //ga na vorige vraag in de quiz.....setup methode aanroep
    }

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showManageUserScene();
    }
}
