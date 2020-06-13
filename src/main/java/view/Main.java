package view;

import CouchDBControllers.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;

public class Main extends Application {

    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    public static ArrayList<User> userList = new ArrayList<>(); //Lijst met dummy-gebruikers

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Make IT Work - Project 1");
        getSceneManager().setWindowTool();
        primaryStage.show();
        CouchDBaccessTest();
    }

    private void CouchDBaccessTest() {
        System.out.println("begin CouchDB test");
        Teacher user = new Teacher(1337, "Stefan", "Stefan", "Docent");
        Answer answer = new Answer(5,"Washington");
        Question question = new Question(1,"Hoofdstad VS");
        Course course = new Course("Maanvissen", 5);
        Group group = new Group(40, "STAMgasten", user);

 //       UserCouchDBController userCouchDBController = new UserCouchDBController();
        AnswerCouchDBcontroller answerCouchDBcontroller = new AnswerCouchDBcontroller();
        QuestionCouchDBController questionCouchDBController = new QuestionCouchDBController();
   //     CourseCouchDBController courseCouchDBController = new CourseCouchDBController();
    //    GroupCouchDBController groupCouchDBController = new GroupCouchDBController();

        answerCouchDBcontroller.saveAnswer(answer);
      //  userCouchDBController.saveUser(user);
        questionCouchDBController.saveQuestion(question);
       // courseCouchDBController.saveCourse(course);
        //groupCouchDBController.saveGroup(group);
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

}