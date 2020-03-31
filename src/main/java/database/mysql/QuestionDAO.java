package database.mysql;

import model.Answer;
import model.Question;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            Quiz quiz = new Quiz(1,"bla",50);
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Question questions;
            while (resultSet.next()) {
                int questionId = resultSet.getInt("vraagId");
                String question = resultSet.getString("vraag");
                int quizID = resultSet.getInt("quiznummer");
                int answerId = resultSet.getInt("cursusId");
                questions = new Question(questionId, question, new Quiz(quizID,"bla", 50),new Answer(answerId,"blbla") );
                                                ///moet nog een QUIZ DAO komen en een ANTOORD DAO KOMENT OM DIT HELEMAAL GOED TE DOEN.
                result.add(questions);
            }
        } catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return  result;
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
