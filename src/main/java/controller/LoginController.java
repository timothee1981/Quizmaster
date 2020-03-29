package controller;

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
        // totdat we een database hebben, gebruiken we een Arraylist met gebruikers
        boolean userNameKnown = false;
        for (User user: Main.userList){
            if(user.getUserName().equals(usernameInput)){
                userNameKnown = true;
            }
        }

        // gebruikersnaam is onbekend
        if(!userNameKnown){
            showErrorMessage("Gebruikersnaam en/of wachtwoord onjuist. Probeer het nog eens.");
            return;
        }

        // checken of wachtwoord ingevoerd is gelijk aan wachtwoord user
        // van de opgehaalde user, check of wachtwoord gelijk is aan ingevoerd wachtwoord
        // input ophalen uit wachtwoord vakje
        String wachtwoordInput = passwordField.getText();
                // haal wachtwoord op, op basis van username
        //TODO:
        // gebruik userDAO om gebruikersgegevens op te halen en stop resultaat in opgehaaldeWachtwoord
        String opgehaaldeWachtwoord = ""; // functie: haalWachtwoordOpObvGebruikersnaam(Gebruikersnaam)

        for (User user: Main.userList){
            if(user.getUserName().equals(usernameInput)){
                opgehaaldeWachtwoord = user.getPassword();
            }
        }

        // TODO nog koppelen aan DAO -> in een try-catch zetten
        // controleren of een gebruiker een geldig wachtwoord invoert.

        // check of deze gelijk zijn -> zo niet -> geef foutmelding
        if (!opgehaaldeWachtwoord.equals(wachtwoordInput)) {
            showErrorMessage("Gebruikersnaam en/of wachtwoord onjuist. Probeer het nog eens.");
            return;
        }

        // kijken welke rol iemand heeft
        int userId = 0;
        String userName = "";
        String userPassword = "";
        String roleUser = "";
        for (User user: Main.userList){
            if(user.getUserName().equals(usernameInput)){
               roleUser = user.getRole();
               userName = user.getUserName();
               userPassword = user.getPassword();
               userId = user.getUserId();
            }
        }

        // Menu-items per rol laden
        switch (roleUser){
            case "Student":
                loggedInUsers.add(new Student(userId, userName, userPassword, roleUser));
                break;
            case "Docent":
                loggedInUsers.add(new Teacher(userId, userName, userPassword, roleUser));
                break;
            case "Coordinator":
                loggedInUsers.add(new Coordinator(userId, userName, userPassword, roleUser));
                break;
            case "Administrator":
                loggedInUsers.add(new Administrator(userId, userName, userPassword, roleUser));
                break;
            case "Technisch beheerder":
                loggedInUsers.add(new TechnicalAdministrator(userId, userName, userPassword, roleUser));
                break;
            default:
                break;
            }

        // Ga naar welkomscherm
        Main.getSceneManager().showWelcomeScene();
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
