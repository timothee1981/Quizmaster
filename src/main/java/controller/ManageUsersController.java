package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Teacher;
import model.User;
import view.Main;

import java.util.ArrayList;

public class ManageUsersController {

    @FXML
    private ListView userList;

    public void setup() {

        // clear list view
        userList.getItems().clear();

        // get all users
        ArrayList<User> userArrayList = getAllUsers();

        // show users in grid/box-thing
        for(User user:userArrayList) {
            userList.getItems().add(user);
        }
    }

    private ArrayList<User> getAllUsers() {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        ArrayList<User> userArrayList =  userDAO.getAll();
        // sluit database connectie
        dbAccess.closeConnection();
        return userArrayList;
    }

    public void doMenu() {
        // go to welcome-menu
        Main.getSceneManager().showWelcomeScene();
    }

    public void doCreateUser() {
        // navigeer naar create-user pagina en toon leeg scherm
        // navigeer naar create-update-user pagina en toon data van user
        Main.getSceneManager().showCreateUpdateUserScene(new Teacher());
    }

    public void doUpdateUser() {
        // haal geselecteerde gebruiker op
        User user = (User)userList.getSelectionModel().getSelectedItem();

        // navigeer naar create-update-user pagina en toon data van user
        Main.getSceneManager().showCreateUpdateUserScene(user);
    }

    public void doDeleteUser() {

        // haal geselecteerde gebruiker op
        User user = (User)userList.getSelectionModel().getSelectedItem();

        // delete user
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        userDAO.deleteUser(user);
        // sluit database connectie
        dbAccess.closeConnection();

        // toon melding dat user verwijderd is
        String informationMessage = String.format("De gebruiker met de username: %s is verwijderd.",user.getUserName());
        showInformationMessage(informationMessage);

        // draai setup van pagina nogmaals (page-refresh)
        setup();
    }

    private void showInformationMessage(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(informationMessage);
        alert.show();
    }
}
