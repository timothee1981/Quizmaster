package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Group;
import model.User;

public class GroupCouchDBDAO {

    private CouchDBaccess db;
    private Gson gson;

    public GroupCouchDBDAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleGroup(Group group) {
        String jsonstring = gson.toJson(group);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public Group getGroupByDocId(String doc_Id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
        Group resultaat = gson.fromJson(json, Group.class);
        return resultaat;
    }

}
