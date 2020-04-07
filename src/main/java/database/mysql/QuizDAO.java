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


                quiz = new Quiz(question,cesuur);

                int cursusId = resultSet.getInt("cursusId");
                quiz.setCourseId(cursusId);

                int quizId = resultSet.getInt("quiznummer");
                quiz.setQuizId(quizId);

                // haal alle vragen op die bij de quiz horen en voeg ze toe als arrayList
                QuestionDAO questionDAO = new QuestionDAO(dBaccess);
                ArrayList questionObjectList = questionDAO.getAllQuestionByQuizId(quizId);
                for(Object questionObject: questionObjectList){
                    quiz.voegQuestionAanQuiz((Question)questionObject);
                }

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
            preparedStatement.setInt(3,quiz.getCourseId());
            int key = executeInsertPreparedStatement(preparedStatement);
            quiz.setQuizId(key);


        }catch (SQLException sqlFout){
            System.out.println(sqlFout);
        }

    }

    public void deleteQuiz(Quiz quiz) {

        String sql = "DELETE FROM quiz WHERE quiznummer = ?";

        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        try{
            // verwijder eerst de quizVragen:
            for(Question question: quiz.getQuestions()){
                questionDAO.deleteQuestion(question);
            }

            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, quiz.getQuizId());
            executeManipulatePreparedStatement(preparedStatement);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void updateQuiz(Quiz quiz){


        String sql = "UPDATE quiz SET  quizNaam = ? , cesuur = ? WHERE quiznummer = ?; ";

        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setDouble(2, quiz.getCesuur());
            preparedStatement.setInt(3, quiz.getQuizId());
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
                quiz.setCourseId(resultSet.getInt("cursusId"));
                quizzes.add(quiz);

                // haal vragen op die bij quiz horen en voeg deze toe aan quiz
                QuestionDAO questionDAO = new QuestionDAO(dBaccess);
                ArrayList<Question> questionArrayList = questionDAO.getAllQuestionByQuizId(quiz.getQuizId());
                for(Question question:questionArrayList){
                    quiz.voegQuestionAanQuiz(question);
                }
            }

        }catch (SQLException sqlFout){
            System.out.println(sqlFout);

        }
        return quizzes;
    }


}
