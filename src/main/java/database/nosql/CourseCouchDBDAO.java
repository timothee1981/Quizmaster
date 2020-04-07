package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Course;

public class CourseCouchDBDAO {

    //Attributen
    private CouchDBaccess dBaccess;
    private Gson gson;

    //Constructor
    public CourseCouchDBDAO(CouchDBaccess dBaccess){
        super();
        this.dBaccess = dBaccess;
        gson = new Gson();
    }

    //methodes
    //Een Jsonstring en document maken van een cursusobject en dat in CouchDB opslaan
    public String saveSingleCourse(Course course){
        String jsonstring = gson.toJson(course);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = dBaccess.saveDocument(jsonObject);
        return doc_Id;
    }

    //Een specifieke cursus ophalen uit CouchDB, op basis van de docId in CouchDB
    public Course getCourseByDocId(String doc_Id){
        JsonObject json = dBaccess.getClient().find(JsonObject.class, doc_Id);
        Course resultaat = gson.fromJson(json, Course.class);
        return resultaat;
    }


}
