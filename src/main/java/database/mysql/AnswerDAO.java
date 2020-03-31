package database.mysql;

import model.Answer;
import model.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDAO extends AbstractDAO{
    public AnswerDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    public ArrayList<Answer> getAllQuestions() {

        String sql = "SELECT * FROM Antwoord";
        ArrayList<Answer> result = new ArrayList<>();
        try {

            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                int answerId = resultSet.getInt("aantwoordId");
                String answerString = resultSet.getString("antwoord");
                Answer answer = new Answer(answerId, answerString);
                result.add(answer);
            }
        } catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return  result;
    }

    public Answer getAnswerById(int answerId){

        Answer answer = null;
        String sql = "SELECT * FROM hond WHERE chipnr = ?;";
        try {

            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,answerId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String answerString = resultSet.getString("antwoord");
                answer = new Answer(answerId,answerString);
            }
        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

        return answer;
    }
}
