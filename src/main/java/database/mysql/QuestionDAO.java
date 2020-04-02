package database.mysql;

import model.Answer;
import model.Question;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionDAO extends AbstractDAO {
    public QuestionDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    //haal all vragen op

    /**
     * @return  haal all vragen die in DB bestaan
     * is een begin
     * DAO QUIZ en DAO vraag moet noog af om dit helemaal goed te doen
     */
    public ArrayList<Question> getAllQuestions() {
        String sql = "SELECT * FROM quizvraag";
        ArrayList<Question> result = new ArrayList<>();
        try {
            AnswerDAO answerDAO = new AnswerDAO(dBaccess);
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Question questions;
            while (resultSet.next()) {
                String question = resultSet.getString("vraag");
                questions = new Question( question);
                result.add(questions);
            }
        } catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return  result;
    }

    /**
     * @param questionId
     * @return
     * geeft vraag terug vanuit DB op basis van vraagId
     */
    public Question getQuestionById(int questionId){
        Question question = null;
        String sql = "SELECT * FROM quizvraag WHERE vraagId = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,questionId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String questionString = resultSet.getString("vraag");
                question = new Question(questionString);

            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }

        return question;
    }


    /**
     * @param questionString
     * @return
     *
     * geeft een vraag terug op basis van vraag
     */
    public Question getQuestionByQuestion(String questionString){
        AnswerDAO answerDAO = new AnswerDAO(dBaccess);
        Question question = null;
        String sql = "SELECT * FROM quizvraag WHERE vraag = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1,questionString);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String question1= resultSet.getString("vraag");
                question = new Question( question1);
            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }
        return question;
    }





    /**
     * @param //questionId
     * @param //question
     * @param //quiz
     * @param //goodAnswer
     *
     * Sla vragen op in DB
     *
     */
    public void storeNewQuestion(Question question){


        String sql = "INSERT INTO quizvraag VALUES(?,?,?,?);";

        try{
            Quiz quiz = new Quiz();

            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setInt(1,question.getQuestionId());
            preparedStatement.setString(2,question.getQuestion());
            preparedStatement.setInt(3,question.getQuiz().getQuizId());
            preparedStatement.setInt(4,question.getAnswers().get(0).getAnswerId());
            executeInsertPreparedStatement(preparedStatement);



        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

    }
}
