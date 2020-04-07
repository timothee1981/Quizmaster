package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Answer;
import model.User;

import java.util.List;

public class AnswerCouchDBDAO {
        private CouchDBaccess db;
        private Gson gson;

        public AnswerCouchDBDAO(CouchDBaccess db) {
            super();
            this.db = db;
            gson = new Gson();
        }

        public String saveSingleQuestion(Answer answer) {
            String jsonstring = gson.toJson(answer);
            System.out.println(jsonstring);
            JsonParser parser = new JsonParser();
            JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
            String doc_Id = db.saveDocument(jsonobject);
            return doc_Id;
        }

        public Answer getAnswerByDocId(String doc_Id) {
            JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
            Answer resultaat = gson.fromJson(json, Answer.class);
            return resultaat;
        }

    public Answer getAnswer(String answerText) {
        Answer answer = null;
        List<JsonObject> allAnswers = db.getClient().view("antwoord").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allAnswers) {
            answer = gson.fromJson(json, Answer.class);
            if (answer.getAnswer().equals(answerText)) {
                return answer;
            }
        }
        return answer;
    }

    }

