package controller;


import database.mysql.DBAccess;
import database.mysql.RoleDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Teacher;
import view.Main;
import javafx.scene.control.*;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.User.DEFAULT_USER_ID;

public class CreateUpdateUserController {


    @FXML
    public ComboBox roleComboBox;
    @FXML
    public Label headerLabel;
    @FXML
    public Button addNewUserButton;
    @FXML
    private TextField userIdTextField;
    @FXML
    private Label userIdLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    public void setup(User user) {

        // vul rol-dropdown met alle rollen
        fillRoleDropdown();

        // check of user een echte user is of een dummy (id = DEFAULT_USER_ID)
        if(user.getUserId() == User.DEFAULT_USER_ID){
            // toon velden voor aanmaak NIEUWE USER -> verberg id-veld
            setupForNewUser();
        } else{
            // vul waarden van velden met velden van de AAN TE PASSEN user
            setupForExistingUser(user);
        }
    }

    private void setupForNewUser() {
        // toon velden voor aanmaak NIEUWE USER -> verberg id-veld
        userIdTextField.setVisible(false);
        userIdLabel.setVisible(false);
        headerLabel.setText("Maak een nieuwe gebruiker aan");
        addNewUserButton.setText("Voeg nieuwe gebruiker toe");
    }

    private void setupForExistingUser(User user) {
        // vul waarden van velden met velden van de AAN TE PASSEN user
        fillTextFieldsOfUser(user);
        setRoleDropdownOfUser(user);
        headerLabel.setText("Pas een bestaande gebruiker aan");
        addNewUserButton.setText("Pas de gebruiker aan");
    }

    private void setRoleDropdownOfUser(User user) {
        roleComboBox.setValue(user.getRole());
    }

    private void fillTextFieldsOfUser(User user) {
        //setId
        userIdTextField.setText(String.valueOf(user.getUserId()));
        //set name
        usernameTextField.setText(user.getUserName());
        //set password
        passwordTextField.setText(user.getPassword());
    }

    private ArrayList<String> getAllRoleItems() {
        // get roles from database
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        RoleDAO roleDAO = new RoleDAO(dbAccess);
        ArrayList<String> roleStrings = roleDAO.getAllRoles();
        dbAccess.closeConnection();

        return roleStrings;
    }

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showManageUserScene();
    }

    public void doCreateUpdateUser() {
        // get role of user - and check validity
        Object userRole = null;
        userRole = roleComboBox.getValue();
        if(userRole == null){
            showErrorMessage("Er is geen rol geselecteerd");
            return;
        }
        String userRoleInput = userRole.toString();

        // get userInput
        String userInputUsername = usernameTextField.getText();
        String userInputPassword = passwordTextField.getText();

        // validate username and password
        if((!validateUsername(userInputUsername))||(!validatePassword(userInputPassword))){
            return;
        }

        String userInputIdString = userIdTextField.getText();
        int userInputIdInt;
        if(userInputIdString.trim().isEmpty()){
            // maak nieuwe user aan
            createNewUser(userInputUsername, userInputPassword, userRoleInput);
            showInformationMessage(String.format("De nieuwe gebruiker met de username: %s is aangemaakt.",userInputUsername));
        } else{
            // pas bestaande gebruiker aan:
            // first get user Id
            try {
                userInputIdInt = Integer.parseInt(userInputIdString);
            } catch(NumberFormatException e){
                showErrorMessage("Het id moet een geheel getal zijn.");
                return;
            }
            // update user
            updateUserById(userInputIdInt, userInputUsername, userInputPassword, userRoleInput);
            showInformationMessage(String.format("De gebruiker met de username: %s is aangepast.",userInputUsername));
        }
        // navigate back to menu
        doMenu();
    }

    private boolean validatePassword(String password) {
        if(password.trim().equals("")){
            // wachtwoord moet gevuld zijn
            showErrorMessage("Het wachtwoord is niet ingevuld");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateUsername(String username) {
        if(username.trim().equals("")){
            // gebruikersnaam moet gevuld zijn
            showErrorMessage("De gebruikersnaam is niet ingevuld");
            return false;
        } else {
            return true;
        }
    }

    private void updateUserById(int id, String username, String password, String role) {
        //update user in database by id
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        userDAO.updateUser(id, username, password, role);
        dbAccess.closeConnection();
    }

    private void createNewUser(String username, String password, String role) {
        //Save new User in database
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        userDAO.storeNewUser(username, password, role);
        dbAccess.closeConnection();
    }

    private void fillRoleDropdown() {
        ArrayList<String> roleStrings = getAllRoleItems();
        for(String roleString: roleStrings){
            roleComboBox.getItems().add(roleString);
        }
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
