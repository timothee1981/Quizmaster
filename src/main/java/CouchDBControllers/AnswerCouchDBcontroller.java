package CouchDBControllers;

import database.nosql.CouchDBaccess;
import database.nosql.AnswerCouchDBDAO;
import model.Answer;

public class AnswerCouchDBcontroller {
    private CouchDBaccess db;
    private AnswerCouchDBDAO answerCouchDBDAO;

    public AnswerCouchDBcontroller(){
        super();
        db = new CouchDBaccess();
        answerCouchDBDAO = new AnswerCouchDBDAO(db);
    }

    public void saveAnswer(Answer answer){
        try {
            db.setupConnection();
            System.out.println("Connection open");
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }
        answerCouchDBDAO.saveSingleQuestion(answer);

    }
}
