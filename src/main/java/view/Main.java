package view;

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
        createUserList();
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Make IT Work - Project 1");
        getSceneManager().setWindowTool();
        primaryStage.show();
    }
    //Testgegevens van testgebruikers
    private void createUserList() {
        userList.add(new Teacher(1, "Stefan", "Stefan", "Docent"));
        userList.add(new Student(2, "Timothee", "Timothee", "Student"));
        userList.add(new Administrator(3, "Mireille", "Mireille", "Administrator"));
        userList.add(new TechnicalAdministrator(4, "Ankie", "Ankie", "Technisch beheerder"));
        userList.add(new Coordinator(5,"Michel","Michel","Coordinator"));
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}