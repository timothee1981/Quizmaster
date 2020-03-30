package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.*;
import view.Main;

import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;

    public static ArrayList<User> loggedInUsers = new ArrayList<>();

    public void doLogin() {

        // check of user bestaat
        // input ophalen uit username-vakje
        String usernameInput = nameTextField.getText();

        // check of username-input een waarde heeft -> als leeg, geef melding
        if(usernameInput.trim().isEmpty()){
            showErrorMessage("Gebruikersnaam en/of wachtwoord onjuist. Probeer het nog eens.");
            return;
        }

        // check of deze user bestaat in het systeem
        // haal alle info van user op uit database
        User userToLogin;
        userToLogin = getUserByUsername(usernameInput);
        if(userToLogin == null){
            showErrorMessage("Gebruikersnaam en/of wachtwoord onjuist. Probeer het nog eens.");
            return;
        }

        // checken of wachtwoord ingevoerd is gelijk aan wachtwoord user
        // bepaal ingevoerde wachtwoord en wachtwoord waarmee het vergeleken moet worden
        String wachtwoordInput = passwordField.getText();
        String opgehaaldeWachtwoord = userToLogin.getPassword();

        // controleren of een gebruiker een geldig wachtwoord invoert, zo niet: geef foutmelding
        if (!opgehaaldeWachtwoord.equals(wachtwoordInput)) {
            showErrorMessage("Gebruikersnaam en/of wachtwoord onjuist. Probeer het nog eens.");
            return;
        }

        // user mag inloggen ->
        User loggedInUser = userToLogin;

        // voeg user toe aan lijst met ingelogde users
        loggedInUsers.add(loggedInUser);

        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
    }

    private User getUserByUsername(String username) {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        User user = userDAO.getUserByUsername(username);
        // sluit database connectie
        dbAccess.closeConnection();
        return user;
    }

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
    }

    // sluit applicatie
    public void doQuit() {
            System.exit(0);
    }
}
