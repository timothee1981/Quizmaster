package database.nosql;

import CouchDBControllers.AnswerCouchDBcontroller;
import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import model.Answer;
import model.Question;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerCouchDBDAOTest {

    @Test
    void getAnswer() {

    }

    @Test
    void getAnswerByDocId() {

        //creer dbcouch object
        CouchDBaccess couchDBaccess = new CouchDBaccess();
        //connectie met couchdb
        try {
            couchDBaccess.setupConnection();
            System.out.println("Connection open");
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }
        //creer een AntwoordcouchDBAOcontroller instantie
        AnswerCouchDBDAO answerCouchDBDAO = new AnswerCouchDBDAO(couchDBaccess);
        //haal antwoord uit couch db
        Answer answerExpected = answerCouchDBDAO.getAnswerByDocId("8fe39325cf2641a08669bca4e67cade9");

        //sla op als expected antwoord

        //creer dbobject
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        //connectie met dbsql
        dbAccess.openConnection();
        //creer een VraagDAO instantie
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        //haal id goede antwoord in vraag op die goede aantwoord geef van hoofstad van Vs
        int idGoedeVraag = questionDAO.getGoodAnswer(123);
        //creer een AntwoordDAO instantie
        AnswerDAO answerDAO = new AnswerDAO(dbAccess);
        //haal antwoord op op basis van de id vraag
        Answer answerToBeTested = answerDAO.getOneById(idGoedeVraag);
        //sla aantwoord op als antwoord to check
        //sluit dbsql connectie
        dbAccess.closeConnection();
        //vergelijke beide antwoorden

        Assert.assertEquals(answerExpected,answerToBeTested);
    }
}