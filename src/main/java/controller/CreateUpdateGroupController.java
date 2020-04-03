package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.Group;
import model.Teacher;
import model.User;
import view.Main;

import java.util.ArrayList;


public class CreateUpdateGroupController {
    @FXML
    public TextField groupName;
    @FXML
    public ComboBox teacherDropdown;


    private Group group;

    public void setup(Group group) {
        // teacherDropdown vullen met items
        // haal alle docenten op
        // geef lijst van docenten
        ArrayList<Teacher> teacherArrayList = getTeachers();
        for (Teacher teacher:teacherArrayList) {
            teacherDropdown.getItems().add(teacher);
        }
        // bepalen of het een bestaande groep is of een nieuwe groep
        // als het een nieuwe groep is: geen actie
        // als het een bestaande groep is: velden vullen
    }

    private ArrayList<Teacher> getTeachers() {
        // todo: teachers ophalen
        // alle users ophalen
        ArrayList<User> userList = new ArrayList<>();

        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        userList = userDAO.getAll();
        // sluit database connectie
        dbAccess.closeConnection();

        // teachers eruit filteren
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        for (User user: userList) {
            if (user instanceof Teacher) {
                teacherArrayList.add((Teacher) user);
            }
        } return teacherArrayList;

    }


    // ga naar het Welkomscherm door op de knop 'menu' te klikken
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    // ga naar het scherm Groepenbeheer door op de knop 'Terug naar Groepenbeheer' te klikken
    public void doCreateUpdateGroup() {
        Main.getSceneManager().showManageGroupsScene();
    }

    // ga naar het scherm Cursusbeheer door op de knop 'Naar Cursusbeheer' te klikken
    public void doCreateUpdateCourse() {
            Main.getSceneManager().showManageCoursesScene();
    }



}
