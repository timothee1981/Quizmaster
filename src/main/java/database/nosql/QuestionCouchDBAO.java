package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Answer;
import model.Question;

import java.util.List;

public class QuestionCouchDBAO {

    private CouchDBaccess db;
    private Gson gson;

    public QuestionCouchDBAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleQuestion(Question question) {
        String jsonstring = gson.toJson(question);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public Question getQuestionByDocId(String doc_Id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
        Question resultaat = gson.fromJson(json, Question.class);
        return resultaat;
    }

    public Question getQuestion(String questionText) {
        Question resultaat = null;
        List<JsonObject> allQuestion = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allQuestion) {
            resultaat = gson.fromJson(json, Question.class);
            if (resultaat.getQuestion().equals(questionText)) {
                return resultaat;
            }
        }
        return resultaat;
    }
}
