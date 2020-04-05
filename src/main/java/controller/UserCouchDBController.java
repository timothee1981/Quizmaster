package controller;

import database.nosql.CouchDBaccess;
import database.nosql.UserCouchDBDAO;
import model.Teacher;
import model.User;

public class UserCouchDBController {

    private CouchDBaccess db;
    private UserCouchDBDAO cdbf;

    public UserCouchDBController(){
        super();
        db = new CouchDBaccess();
        cdbf = new UserCouchDBDAO(db);
    }

    public void saveUser(User user){
        try {
            db.setupConnection();
            System.out.println("Connection open");
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }
        cdbf.saveSingleUser(user);
    }

}
