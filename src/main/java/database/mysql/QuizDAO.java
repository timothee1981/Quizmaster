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
                double cesuur = resultSet.getDouble("cesuur");
                int courseId = resultSet.getInt("cursusId");
                quiz = new Quiz(question,cesuur);
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
                String quizname = resultSet.getString("quizNaam");
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


        String sql = "INSERT INTO quiz(quizNaam,cesuur,cursusId) VALUES(?,?,?);";

        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1,quiz.getQuizName());
            preparedStatement.setDouble(2,quiz.getCesuur());
            preparedStatement.setInt(3,1);
            int key = executeInsertPreparedStatement(preparedStatement);
            quiz.setQuizId(key);


        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

    }

    public void deleteQuizBiId(int quizId) {

        String sql = "DELETE FROM quiz WHERE quiznummer = ?";

        try{

            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, quizId);
            executeManipulatePreparedStatement(preparedStatement);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void updateQuiz(Quiz quiz){


        String sql = "UPDATE quiz SET  quizNaam = ? WHERE quiznummer = ?; ";

        try{

            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setInt(2, quiz.getQuizId());
            executeManipulatePreparedStatement(preparedStatement);


        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Quiz> getAllQuizbyCursusId(int cursusnummer){
        ArrayList<Quiz> quizzes = new ArrayList<>();
       Quiz quiz = null;
        String sql = "SELECT * FROM quiz WHERE cursusId = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,cursusnummer);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String quizName = resultSet.getString("quizNaam");
                Double cesuur = resultSet.getDouble("cesuur");
                quiz = new Quiz(quizName,cesuur);
                quiz.setQuizId(resultSet.getInt("quiznummer"));
                quizzes.add(quiz);
            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }
        return quizzes;
    }


}
