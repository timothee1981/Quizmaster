package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Course;
import view.Main;

public class ManageCoursesController {

    @FXML
    private Button newCourseButton;

    @FXML
    private Button wijzigCursusButton;

    @FXML
    private Button verwijderCursusButton;

    @FXML
    private Button terugNaarMenuButton;


    public void setup() {}
    //Opvragen van alle cursussen
    //ArrayList<Course> cursusLijst = CreateUpdateCourseController.getCourses();

    //Ga naar het menu met taken van de Administrator
    public void doMenu(){
        Main.getSceneManager().showWelcomeScene();
    }
    //Toevoegen van een nieuwe cursus
    public void doCreateCourse(){
        Main.getSceneManager().showCreateUpdateCourseScene(new Course());
    }

    //Wijzigen van een cursus(id, naam en coordinator)
    public void doUpdateCourse(){}

    //Verwijderen van een cursus
    public void doDeleteCourse(){}

}
