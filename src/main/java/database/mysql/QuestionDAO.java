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
                int questionId = resultSet.getInt("vraagId");
                String question = resultSet.getString("vraag");
                Answer answer = answerDAO.getAnswerById(resultSet.getInt("aantwoordjuist"));
                questions = new Question(questionId,question,answer);
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
        AnswerDAO answerDAO = new AnswerDAO(dBaccess);
        Question question = null;
        String sql = "SELECT * FROM quizvraag WHERE vraagId = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,questionId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                int vraagId = resultSet.getInt("vraagId");
                String questionString = resultSet.getString("vraag");
                Answer answer = answerDAO.getAnswerById(resultSet.getInt("aantwoordjuist"));
                question = new Question(vraagId,questionString,answer);

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
                int vraagId = resultSet.getInt("vraagId");
                String question1= resultSet.getString("vraag");
                Answer answer = answerDAO.getAnswerById(resultSet.getInt("aantwoordjuist"));
                question = new Question(vraagId,question1,answer);
            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }
        return question;
    }





    /**
     * @param questionId
     * @param question
     * @param quiz
     * @param goodAnswer
     *
     * Sla vragen op in DB
     *
     */
    public void storeNewQuestion(int questionId, String question, Quiz quiz, Answer goodAnswer){


        String sql = "INSERT INTO quizvraag VALUES (?,?,?,?);";
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,questionId);
            preparedStatement.setString(2,question);
            preparedStatement.setInt(3, quiz.getQuizId());
            preparedStatement.setInt(4,goodAnswer.getAnswerId());
            preparedStatement.executeUpdate();

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

    }
}
