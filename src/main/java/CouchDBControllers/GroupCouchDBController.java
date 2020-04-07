package CouchDBControllers;

import database.nosql.CouchDBaccess;
import database.nosql.GroupCouchDBDAO;
import database.nosql.UserCouchDBDAO;
import model.Group;
import model.User;

public class GroupCouchDBController {

    private CouchDBaccess db;
    private GroupCouchDBDAO cdbf;

    public GroupCouchDBController(){
        super();
        db = new CouchDBaccess();
        cdbf = new GroupCouchDBDAO(db);
    }

    public void saveGroup(Group group){
        try {
            db.setupConnection();
            System.out.println("Connection open");
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }
        cdbf.saveSingleGroup(group);
    }

}
