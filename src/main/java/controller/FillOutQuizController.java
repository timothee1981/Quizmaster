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
import model.QuizResult;
import view.Main;

import java.util.ArrayList;
import java.util.Collections;

public class FillOutQuizController {
    private DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private Quiz quiz;
    private QuizDAO quizDAO;
    private int questionCount = 0;
    private String answer1,answer2,answer3,answer4;
    private String goodAnswer;
    private QuizResult quizResult;
    private int antwoordenJuist;





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
        showQuestion(getAllQuestionFromQuiz,questionCount);

        dbAccess.closeConnection();



    }

    private void showQuestion(ArrayList<Question> getAllQuestion,  int questionCount) {
        dbAccess.openConnection();
        this.questionDAO = new QuestionDAO(dbAccess);
        this.answerDAO = new AnswerDAO(dbAccess);


        //welke vraag willen we
        Question question = getAllQuestion.get(questionCount);

        int goedeAnswerId = questionDAO.getGoodAnswer(question.getQuestionId());
        goodAnswer = answerDAO.getOneById(goedeAnswerId).getAnswer();

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
        //answers op andere index mixen
        Collections.shuffle(answers);
        //waarde verschillende antworden opslaan
        answer1  = answers.get(0).getAnswer();
        answer2 = answers.get(1).getAnswer();
        answer3 = answers.get(2).getAnswer();
        answer4 = answers.get(3).getAnswer();

        //geef een integer waarde aan de anwtoorden string
        int count = 1;
        //print deze antwoorden uit
        for(int arrayteller =0; arrayteller<answers.size();arrayteller++) {
            questionArea.appendText("vraag " + count++ + ": " + answers.get(arrayteller).getAnswer() + "\n");
        }

        dbAccess.closeConnection();
    }

    public void doRegisterA() {

        if(answer1.equals(goodAnswer)) {
            System.out.println("well done");
            antwoordenJuist++;

        }else{
            System.out.println("pity");
        }


    }

    public void doRegisterB() {
        if(answer2.equals(goodAnswer)) {
            System.out.println("well done");
            antwoordenJuist++;

        }else{
            System.out.println("pity");
        }
    }

    public void doRegisterC() {
        if(answer3.equals(goodAnswer)) {
            System.out.println("well done");
            antwoordenJuist++;

        }else{
            System.out.println("pity");
        }
    }

    public void doRegisterD() {
        if(answer4.equals(goodAnswer)){
            System.out.println("well done");
            antwoordenJuist++;

        }else{
            System.out.println("pity");
        }
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
