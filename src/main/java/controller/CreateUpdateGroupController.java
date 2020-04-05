package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
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
            // setId in hidden textfield
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
        Teacher teacher = null;
        try{
        teacher = (Teacher)teacherDropdown.getValue();
        } catch (NullPointerException e){
            showErrorMessage("Er is geen docent geselecteerd.");
        }

        // get Id
        String groupIdString = groupIdTextbox.getText();

        // If id = not filled -> create new Group
        if(groupIdString == null || groupIdString.trim().equals("")){
            // maak nieuwe group, zet waarden erin, maar vul id niet in
            Group group = new Group();
            group.setGroepnaam(groupname);
            group.setTeacher(teacher);

            // sla groep op al nieuwe groep
            saveNewGroup(group);

        } else{
            // if id = filled -> update Group
            // zet Id-string om in int
            int groupId = 0;
            try {
                groupId = Integer.parseInt(groupIdString);
            } catch (NumberFormatException e){
                showErrorMessage("Het groepsId moet een getal zijn.");
                return;
            }
            // maak group-object
            Group group = new Group(groupId, groupname, teacher);
            // sla de groep op via een update
            saveUpdateGroup(group);
        }
    }

    private void saveNewGroup(Group group) {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        // roep save-methode aan
        groupDAO.storeOne(group);
        // sluit database connectie
        dbAccess.closeConnection();
    }

    private void saveUpdateGroup(Group group) {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        // roep save-methode aan
        groupDAO.updateOne(group);
        // sluit database connectie
        dbAccess.closeConnection();
    }

    // ga naar het scherm Cursusbeheer door op de knop 'Naar Cursusbeheer' te klikken
    public void doCreateUpdateCourse() {
            Main.getSceneManager().showManageCoursesScene();
    }

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
    }

    private void showInformationMessage(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(informationMessage);
        alert.show();
    }
}
