package database.nosql;

import CouchDBControllers.AnswerCouchDBcontroller;
import model.Answer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerCouchDBDAOTest {

    @Test
    void getAnswer() {
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
        Answer answer = answerCouchDBDAO.getAnswer("Washington");

        //sla op als expected antwoord
        //sluit couch cd connectie
        //creer dbobject
        //connectie met dbsql
        //creer een Vraag instantie
        //haal vraag op die vraagd welke hoofstad van Vs
        //haal id vraag op
        //creer een Antwoord instantie
        //haal antwoord op op basis van de id vraag
        //sla aantwoord op als antwoord to check
        //sluit dbsql connectie
        //vergelijke beide antwoorden
    }
}