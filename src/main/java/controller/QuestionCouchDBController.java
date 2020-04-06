package controller;

import database.nosql.AnswerCouchDBDAO;
import database.nosql.CouchDBaccess;
import database.nosql.QuestionCouchDBAO;
import model.Answer;
import model.Question;

public class QuestionCouchDBController {

    private CouchDBaccess db;
    private QuestionCouchDBAO questionCouchDBAO;

    public QuestionCouchDBController(){
        super();
        db = new CouchDBaccess();
        questionCouchDBAO = new QuestionCouchDBAO(db);
    }

    public void saveQuestion(Question question){
        try {
            db.setupConnection();
            System.out.println("Connection open");
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }
        questionCouchDBAO.saveSingleQuestion(question);

    }

}
