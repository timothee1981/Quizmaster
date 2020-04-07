package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Teacher;
import model.User;

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

        // bepaal welk subtype het is: (niet null)
        if(teacher != null){
            user = teacher;
        }
        //todo: voor admin / student / techAdmin / coordinator

        return user;
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
