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
                String answerString = resultSet.getString("antwoord");
                Question question = questionDAO.getOneById(resultSet.getInt("vraagId"));
                Answer answer = new Answer(answerString,question);
                answer.setAnswerId(resultSet.getInt("aantwoordId"));
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
        String sql = "SELECT * FROM antwoord WHERE aantwoordId = ?;";
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


    public ArrayList<Answer> getAnswersByQuestionId(int vraagId){

       ArrayList<Answer> answers = new ArrayList<>();
        String sql = "SELECT * FROM antwoord WHERE vraagId = ?;";
        try {
            QuestionDAO questionDAO = new QuestionDAO(dBaccess);
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,vraagId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String answerString = resultSet.getString("antwoord");
                Question question = questionDAO.getOneById(resultSet.getInt("vraagId"));
                Answer answer = new Answer(answerString,question);
                answers.add(answer);
            }
        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

        return answers;
    }

    @Override
    public void storeOne(Object type) {
        Answer answer1 = (Answer) type;
        String sql = "INSERT INTO antwoord(antwoord,vraagId) VALUES (?,?);";
        try {

            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, answer1.getAnswer());
            preparedStatement.setInt(2, answer1.getQuestion().getQuestionId());
            int key = executeInsertPreparedStatement(preparedStatement);
            answer1.setAnswerId(key);

        } catch (SQLException sqlFout) {
            System.out.println(sqlFout);
        }
    }


    public void updateAnswer(Answer answer){

            String sql = "UPDATE antwoord SET  antwoord = ? WHERE aantwoordId = ?; ";
            try{
                // sla data van user op
                PreparedStatement preparedStatement = getStatement(sql);
                preparedStatement.setString(1, answer.getAnswer());
                preparedStatement.setInt(2,answer.getAnswerId());
                preparedStatement.executeUpdate();


            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }



    public void deleteAnswerfromQuestion(int questionId) {
        String sql = "DELETE FROM antwoord WHERE vraagId = ?";

        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, questionId);
            executeManipulatePreparedStatement(preparedStatement);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }











}
