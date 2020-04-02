package database.mysql;

import model.Answer;
import model.Question;
import model.Quiz;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDAO extends AbstractDAO implements GenericDAO{
    public AnswerDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    @Override
    public ArrayList<Answer> getAll() {

        String sql = "SELECT * FROM Antwoord";
        ArrayList<Answer> result = new ArrayList<>();
        try {
            QuestionDAO questionDAO = new QuestionDAO(dBaccess);

            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                int answerId = resultSet.getInt("aantwoordId");
                String answerString = resultSet.getString("antwoord");
                Question question = questionDAO.getOneById(resultSet.getInt("vraagId"));
                Answer answer = new Answer(answerString,question);
                result.add(answer);
            }
        } catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return  result;
    }

    @Override
    public Answer getOneById(int answerId){

        Answer answer = null;
        String sql = "SELECT * FROM hond WHERE chipnr = ?;";
        try {
            QuestionDAO questionDAO = new QuestionDAO(dBaccess);
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,answerId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String answerString = resultSet.getString("antwoord");
                Question question = questionDAO.getOneById(resultSet.getInt("vraagId"));
                answer = new Answer(answerString,question);
            }
        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

        return answer;
    }


    public void storeNewAnswer(Answer answer){
        String sql = "INSERT INTO antwoord(antwoord,vraagId) VALUES (?,?);";
        try{
            QuestionDAO questionDAO = new QuestionDAO(dBaccess);


            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1,answer.getAnswer());
            preparedStatement.setInt(2,answer.getQuestion().getQuestionId());
            int key = executeInsertPreparedStatement(preparedStatement);
            answer.setAnswerId(key);

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }



    }

    @Override
    public void storeOne(Object type) {
        // Id is auto-increment dus Id moet niet meegegeven worden bij het opslaan
        Answer answer1 = (Answer) type;

        String answerString = answer1.getAnswer();
        Question question = answer1.getQuestion();

        storeNewAnswer(new Answer(answerString,question));
    }





}
