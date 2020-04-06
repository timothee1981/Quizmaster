package database.mysql;

import model.Answer;
import model.Question;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionDAO extends AbstractDAO implements GenericDAO{

    public QuestionDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    //haal all vragen op

    /**
     * @return  haal all vragen die in DB bestaan
     * is een begin
     * DAO QUIZ en DAO vraag moet noog af om dit helemaal goed te doen
     */

    @Override
    public ArrayList<Question> getAll() {
        String sql = "SELECT * FROM quizvraag";
        ArrayList<Question> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
            Question question = null;
            while (resultSet.next()) {
                String questions = resultSet.getString("vraag");
                question = new Question( questions);
                question.setQuestionId(resultSet.getInt("vraagId"));
                result.add(question);

            }
            AnswerDAO answerDAO = new AnswerDAO(dBaccess);
            ArrayList<Answer> answers = new ArrayList<>();
            answers = answerDAO.getAnswersByQuestionId(question.getQuestionId());
            for(Answer answer: answers){
                question.voegAntwoordAanVraag(answer);
            }
        } catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return  result;
    }

    /**
     * @param// questionId
     * @return
     * geeft vraag terug vanuit DB op basis van vraagId
     */
    @Override
    public Question getOneById(int questionId){
        Question question = null;
        String sql = "SELECT * FROM quizvraag WHERE vraagId = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,questionId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String questionString = resultSet.getString("vraag");
                question = new Question( questionString);
                question.setQuestionId(resultSet.getInt("vraagId"));
            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }

        return question;
    }


    /**
     * @param //questionString
     * @return
     *
     * geeft een vraag terug op basis van vraag
     */
     public ArrayList<Question> getAllQuestionByQuizId(int quiznummer){
        ArrayList<Question> questions = new ArrayList<>();
        Question question = null;
        String sql = "SELECT * FROM quizvraag WHERE quiznummer = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1,quiznummer);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                String question1= resultSet.getString("vraag");
                question = new Question(question1);
                question.setQuestionId(resultSet.getInt("vraagId"));

                //todo: antwoorden toevoegen
                AnswerDAO answerDAO = new AnswerDAO(dBaccess);
                ArrayList<Answer> answerArrayList = answerDAO.getAnswersByQuestionId(resultSet.getInt("vraagId"));
                for(Answer answer: answerArrayList){
                    question.voegAntwoordAanVraag(answer);
                }
                questions.add(question);
            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }
        return questions;
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
    @Override
    public void storeOne(Object type){
        Question question = (Question) type;


        String sql = "INSERT INTO quizvraag (vraag, antwoordJuist,quiznummer ) VALUES(?,?,?);";

        try{
            AnswerDAO answerDAO = new AnswerDAO(dBaccess);

            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1,question.getQuestion());
            preparedStatement.setInt(2, question.getAnswers().get(0).getAnswerId());
            preparedStatement.setInt(3,question.getQuiz().getQuizId());
            int key = executeInsertPreparedStatement(preparedStatement);
            question.setQuestionId(key);

            for(Answer answer: question.getAnswers()) {
                answerDAO.storeOne(answer);
            }
        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

    }

    public void deleteQuestion(Question question) {


        String sql = "DELETE FROM quizvraag WHERE vraag = ?";

        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, question.getQuestion());
            executeManipulatePreparedStatement(preparedStatement);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateQuestion(Question question){


        String sql = "UPDATE quizvraag SET " +
                " vraag = ? " +
                "WHERE vraagId = ?; ";

        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, question.getQuestion());
            preparedStatement.setInt(2, question.getQuestionId());

            executeManipulatePreparedStatement(preparedStatement);
            AnswerDAO answerDAO = new AnswerDAO(dBaccess);
            for(Answer answer: question.getAnswers()){
                answerDAO.updateAnswer(answer);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateGoedAntwoodId(int answerId, int questionId){

        String sql = "UPDATE quizvraag SET " +
                " antwoordJuist = ? " +
                "WHERE vraagId = ?; ";

        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setInt(1, answerId);
            preparedStatement.setInt(2, questionId);

            executeManipulatePreparedStatement(preparedStatement);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
