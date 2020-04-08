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

        //haal quiz op quizid
        Quiz quizToTest = quizDAO.getOneById(5);

        //sluit connectieDb
        dbAccess.closeConnection();

        Quiz quizexpected = new Quiz(5,"Geography",51,1);

        //vergelijk als quizid die is bij vraag USA overeenkomt met quizid bij Geographie
        Assert.assertEquals(quizToTest, quizexpected);

    }
}