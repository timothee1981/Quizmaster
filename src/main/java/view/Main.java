package view;

import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;

public class Main extends Application {

    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    public static ArrayList<User> userList = new ArrayList<>(); //Lijst met dummy-gebruikers
    public static ArrayList<Course> courseList = new ArrayList<>(); //Lijst met dummy-cursussen
    public static ArrayList<Group> groupList = new ArrayList<>(); // Lijst met dummy-groepen



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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

    //Testgegevens met verzonnen cursussen
    public void createCourseList(){
        courseList.add(new Course(1, "BasisCursus", 100));
        courseList.add(new Course(2, "GevorderdenCursus", 101));
        courseList.add(new Course(3, "ExpertCursus", 102));
    }

    //Testgegevens met verzonnen groepen
    public void createGroupList() {
        groupList.add(new Group(1, "Basisgroep"));
        groupList.add(new Group(2, "Gevorderdengroep"));
        groupList.add(new Group(3, "Expertgroep"));
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}