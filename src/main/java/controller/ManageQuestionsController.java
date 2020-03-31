package controller;

import model.Question;
import view.Main;

public class ManageQuestionsController {

    public void setup() {}

    public void doMenu(){}

    public void doCreateQuestion(){
        Main.getSceneManager().showCreateUpdateQuestionScene(new Question(0,null,null));

    }

    public void doUpdateQuestion(){}

    public void doDeleteQuestion(){}
}
