package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Course;
import view.Main;

public class StudentSignInOutController {

    @FXML
    private ListView<Course> signedOutCourseList;
    @FXML
    private ListView <Course> signedInCourseList;

    public void setup() {}

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
    }

    public void doSignIn() {}

    public void doSignOut() {}
}
