package database.mysql;

import model.Answer;
import model.Question;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizDAO extends AbstractDAO{
    public QuizDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    public Quiz getQuizById(int quizId){

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
                quiz = new Quiz(quiznummer,quizname,cesuur);

            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }

        return quiz;
    }

    public void storeNewQuiz(Quiz quiz){


        String sql = "INSERT INTO quiz VALUES(DEFAULT,?,?,?);";
        try{


            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1,quiz.getQuizName());
            preparedStatement.setDouble(2,quiz.getCesuur());
            preparedStatement.setInt(3,quiz.getCourse().getCursusId());
            preparedStatement.executeUpdate();


        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

    }


}
