package controller;

import database.mysql.CourseDAO;
import model.Course;
import view.Main;

import java.util.ArrayList;

public class ManageCoursesController {

    public void setup() {}
    //Opvragen van alle cursussen
    //ArrayList<Course> cursusLijst = CreateUpdateCourseController.getCourses();

    //Ga naar het menu met taken van de Administrator
    public void doMenu(){
        Main.getSceneManager().showManageCoursesScene();
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
