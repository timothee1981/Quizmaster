package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class UserCouchDBDAO {

    private CouchDBaccess db;
    private Gson gson;

    public UserCouchDBDAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleUser(User user) {
        String jsonstring = gson.toJson(user);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public User getUserByDocId(String doc_Id) {
        // maak lege user aan
        User user = null;

        // loop alle children na:
        Teacher teacher = getTeacherByDocId(doc_Id);
        Administrator administrator = getAdministratorByDocId(doc_Id);
        Student student = getStudentByDocId(doc_Id);
        TechnicalAdministrator technicalAdministrator = getTechnicalAdministratorByDocId(doc_Id);
        Coordinator coordinator = getCoordinatorByDocId(doc_Id);

        // bepaal welk subtype het is: (niet null)
        if(teacher != null){
            user = teacher;
        } else if(administrator != null){
            user = administrator;
        } else if(student != null){
            user = student;
        } else if(technicalAdministrator != null){
            user = technicalAdministrator;
        } else if(coordinator != null){
            user = coordinator;
        }

        // geef user terug - die als juiste child is geïnitialiseerd
        return user;
    }

    private Coordinator getCoordinatorByDocId(String doc_id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_id);
        Coordinator coordinator = null;
        try {
            coordinator = gson.fromJson(json, Coordinator.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return coordinator;
    }

    private TechnicalAdministrator getTechnicalAdministratorByDocId(String doc_id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_id);
        TechnicalAdministrator technicalAdministrator = null;
        try {
            technicalAdministrator = gson.fromJson(json, TechnicalAdministrator.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return technicalAdministrator;
    }

    private Student getStudentByDocId(String doc_id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_id);
        Student student = null;
        try {
            student = gson.fromJson(json, Student.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return student;
    }

    private Administrator getAdministratorByDocId(String doc_id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_id);
        Administrator administrator = null;
        try {
            administrator = gson.fromJson(json, Administrator.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return administrator;
    }

    public Teacher getTeacherByDocId(String doc_id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_id);
        Teacher teacher = null;
        try {
            teacher = gson.fromJson(json, Teacher.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return teacher;
    }
}
