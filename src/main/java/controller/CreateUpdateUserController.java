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

public class CreateUpdateUserController {

    @FXML
    public MenuButton roleMenuButton;
    @FXML
    private Label headerLabel;
    @FXML
    private TextField userIdTextField;
    @FXML
    private Label userIdLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label roleLabel;
    @FXML
    private Button addNewUserButton;
    @FXML
    private Button goToMenu;
    @FXML
    private Button setupButton;

    String userInputRole;

    // deze handler werkt de String userInputRole bij als er een item wijzigt in een dropdownmenu
    // de menu-items moeten zich 'subscriben' voor deze handler, dit wordt gedaan in setup-methode
    // vlak voordat ze worden toegevoegd aan het menu
    EventHandler<ActionEvent> getTextOfSelectedMenuItem = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            userInputRole = ((MenuItem)e.getSource()).getText();
        }
    };

    public void setup(User user) {
        // maak alle invulvakken leeg
        emptyTextFields();

        // koppel rollen-lijst aan comboBox
        // haal rollenlijst op uit database
        ArrayList<MenuItem> roleItems = getAllRoleItems();

        // koppel opgehaalde lijst aan comboBox
        // subscribe items aan 'haal waarde uit menuitem-event'
        for (MenuItem menuItem : roleItems) {
            //subscribe menu-item to event
            menuItem.setOnAction(getTextOfSelectedMenuItem);

            // add to menu
            roleMenuButton.getItems().add(menuItem);
        }
    }

    private ArrayList<MenuItem> getAllRoleItems() {

        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        RoleDAO roleDAO = new RoleDAO(dbAccess);
        ArrayList<String> menuStringItems = roleDAO.getAllRoles();
        dbAccess.closeConnection();

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for(String role:menuStringItems){
            MenuItem menuItem = new MenuItem(role);
            menuItems.add(menuItem);
        }

        return menuItems;
    }

    private void emptyTextFields() {
        //todo: methode aanpassen zodat die zelf alle texfields zoekt en leeg maakt
        userIdTextField.clear();
        usernameTextField.clear();
        passwordTextField.clear();
    }


    public void doMenu() {
        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
    }

    public void doCreateUpdateUser() {

        // check of de input of Id is an empty string -> zo ja, dan wordt er een nieuwe user aangemaakt
        String userInputIdString = userIdTextField.getText();
        String userInputUsername = usernameTextField.getText();
        String userInputPassword = passwordTextField.getText();
        // role is stored globally in class due to event listener
        int userInputIdInt;
        if(userInputIdString.trim().isEmpty()){
            // maak nieuwe user aan
            createNewUser(userInputUsername, userInputPassword, userInputRole);
        } else{
            try {
                userInputIdInt = Integer.parseInt(userInputIdString);
            } catch(NumberFormatException e){
                //todo: show errormessage
                //ERROR ERROR
                return;
            }
            // pas bestaande gebruiker aan
            updateUserById(userInputIdInt, userInputUsername, userInputPassword, userInputRole);
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

    public void fakeSetup(ActionEvent actionEvent) {
        User user = new Teacher(1,"Stefan","Stefan","Docent");
        setup(user);
    }
}
