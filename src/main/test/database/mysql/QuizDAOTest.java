package database.mysql;

import model.Question;
import model.Quiz;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizDAOTest {

    @Test
    void getOneById() {
        //Creer Database object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        //maak connectie met Database
        dbAccess.openConnection();
        //creer quiz instantie
        QuizDAO quizDAO = new QuizDAO(dbAccess);

        //creer vraag instantie
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);

        //haal quiz op quizid op die hoort bij vraag USA
         int quizId = questionDAO.getOneById(123).getQuiz().getQuizId();
         Quiz quizToTest = quizDAO.getOneById(quizId);


        //haal quiz op Geographie....quiz die we verwachten
        Quiz quizexpected = quizDAO.getOneById(5);

        //sluit connectieDb
        dbAccess.closeConnection();

        //vergelijk als quizid die is bij vraag USA overeenkomt met quizid bij Geographie
        Assert.assertEquals(quizToTest, quizexpected);

    }
}