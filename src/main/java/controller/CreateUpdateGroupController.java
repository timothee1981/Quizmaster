package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Group;
import model.Teacher;
import model.User;
import view.Main;

import java.util.ArrayList;


public class CreateUpdateGroupController {
    @FXML
    public TextField groupNameTextBox;
    @FXML
    public ComboBox teacherDropdown;
    @FXML
    public TextField groupIdTextbox;

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
        if(! (group.getGroepId() == Group.DEFAULT_GROUP_ID)){
            // dit is een bestaande group
            // als het een bestaande groep is: velden vullen
            groupNameTextBox.setText(group.getGroepnaam());
            teacherDropdown.setValue(group.getTeacher());
            // setId and hide textbox
            groupIdTextbox.setText(String.format("%d",group.getGroepId()));
            //todo: hide groupIdTextbox


        }
        // als het een nieuwe groep is: geen actie
    }

    private ArrayList<Teacher> getTeachers() {
        // alle users ophalen
        ArrayList<User> userList = getUsers();

        // teachers eruit filteren
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        for (User user: userList) {
            if (user instanceof Teacher) {
                teacherArrayList.add((Teacher) user);
            }
        } return teacherArrayList;
    }

    private ArrayList<User> getUsers(){
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        ArrayList<User> userList = userDAO.getAll();
        // sluit database connectie
        dbAccess.closeConnection();
        return userList;
    }

    // ga naar het Welkomscherm door op de knop 'menu' te klikken
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    // ga naar het scherm Groepenbeheer door op de knop 'Terug naar Groepenbeheer' te klikken
    public void doCreateUpdateGroup() {
        // get values of group to add/update



        // get name
        String groupname = groupNameTextBox.getText();
        // get Teacher
        try{
        Teacher teacher = (Teacher)teacherDropdown.getValue();
        } catch (NullPointerException e){
            //todo: show message to user
            System.out.println("er is geen docent geselecteerd");
        }

        // get Id
        //todo: get Id en bepaal of nieuwe groep of update moet zijn
        
        // If id = not filled -> create new Group

        // if id = filled -> update Group

    }

    // ga naar het scherm Cursusbeheer door op de knop 'Naar Cursusbeheer' te klikken
    public void doCreateUpdateCourse() {
            Main.getSceneManager().showManageCoursesScene();
    }
}
