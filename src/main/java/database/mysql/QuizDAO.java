package database.mysql;

import model.Answer;
import model.Question;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizDAO extends AbstractDAO implements GenericDAO{
    public QuizDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    @Override
    public ArrayList<Quiz> getAll(){

        String sql = "SELECT * FROM quiz";
        ArrayList<Quiz> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
            Quiz quiz;
            while (resultSet.next()) {
                String question = resultSet.getString("quizNaam");
                quiz = new Quiz();
                quiz.setQuizId(resultSet.getInt("quiznummer"));
                result.add(quiz);
            }
        } catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return  result;

    }

    @Override
    public Quiz getOneById(int quizId){

        Quiz quiz = null;
        String sql = "SELECT * FROM quiz WHERE quiznummer = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,quizId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                int quiznummer = resultSet.getInt("quiznummer");
                String quizname = resultSet.getString("quiznaam");
                double cesuur = resultSet.getDouble("cesuur");
                quiz = new Quiz(quizname,cesuur);
                quiz.setQuizId(resultSet.getInt("quiznummer"));

            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }

        return quiz;
    }

    @Override
    public void storeOne(Object t){
        Quiz quiz = (Quiz) t;


        String sql = "INSERT INTO quiz(quiznummer,cesuur,cursusId) VALUES(?,?,?);";

        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1,quiz.getQuizName());
            preparedStatement.setDouble(2,quiz.getCesuur());
            preparedStatement.setInt(3,quiz.getCourse().getCursusId());
            int key = executeInsertPreparedStatement(preparedStatement);
            quiz.setQuizId(key);


        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

    }

    public void deleteQuestion(Quiz quiz) {

        String sql = "DELETE FROM quiz WHERE quiz = ?";

        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            executeManipulatePreparedStatement(preparedStatement);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
