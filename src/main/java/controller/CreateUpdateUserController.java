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

        fillRoleDropdown();

        // check of user een echte user is of een dummy (id = DEFAULT_USER_ID)
        if(user.getUserId() == User.DEFAULT_USER_ID){
            // toon velden voor aanmaak NIEUWE USER -> verberg id-veld
            userIdTextField.setVisible(false);
            userIdLabel.setVisible(false);
            headerLabel.setText("Maak een nieuwe gebruiker aan");
            addNewUserButton.setText("Voeg nieuwe gebruiker toe");
        } else{
            // vul waarden van velden met velden van de AAN TE PASSEN user
            fillTextFieldsOfUser(user);
            setRoleDropdownOfUser(user);
            headerLabel.setText("Pas een bestaande gebruiker aan");
            addNewUserButton.setText("Pas de gebruiker aan");
        }
    }

    private void setRoleDropdownOfUser(User user) {
        roleComboBox.setValue(user.getRole());
    }

    private void fillTextFieldsOfUser(User user) {
        //setId
        int userId = user.getUserId();
        String userIdString = String.format("%d",userId);
        userIdTextField.setText(userIdString);

        usernameTextField.setText(user.getUserName());
        passwordTextField.setText(user.getPassword());
    }

    private ArrayList<String> getAllRoleItems() {

        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        RoleDAO roleDAO = new RoleDAO(dbAccess);
        ArrayList<String> menuStringItems = roleDAO.getAllRoles();
        dbAccess.closeConnection();

        return menuStringItems;
    }

    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showManageUserScene();
    }

    public void doCreateUpdateUser() {

        // check of de input of Id is an empty string -> zo ja, dan wordt er een nieuwe user aangemaakt
        String userInputIdString = userIdTextField.getText();
        String userInputUsername = usernameTextField.getText();
        String userInputPassword = passwordTextField.getText();
        String userRoleInput = "";
        if(!(roleComboBox.getValue() == null)) {
            // roleComboBox has value, now get it!
            userRoleInput = roleComboBox.getValue().toString();
        } else{
            showErrorMessage("Er is geen rol geselecteerd");
        }

        if((!validateUsername(userInputUsername))||(!validatePassword(userInputPassword))){
            return;
        }

        int userInputIdInt;
        if(userInputIdString.trim().isEmpty()){
            // maak nieuwe user aan
            createNewUser(userInputUsername, userInputPassword, userRoleInput);
            String informationMessage = String.format("De nieuwe gebruiker met de username: %s is aangemaakt.",userInputUsername);
            showInformationMessage(informationMessage);
        } else{
            try {
                userInputIdInt = Integer.parseInt(userInputIdString);
            } catch(NumberFormatException e){
                showErrorMessage("Het id moet een geheel getal zijn.");
                return;
            }
            // pas bestaande gebruiker aan
            updateUserById(userInputIdInt, userInputUsername, userInputPassword, userRoleInput);
            String informationMessage = String.format("De gebruiker met de username: %s is aangepast.",userInputUsername);
            showInformationMessage(informationMessage);
        }
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
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        userDAO.updateUser(id, username, password, role);
        // sluit database connectie
        dbAccess.closeConnection();
    }

    private void createNewUser(String username, String password, String role) {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        userDAO.storeNewUser(username, password, role);
        // sluit database connectie
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
