package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Student;
import model.User;
import view.Main;

import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;

    public static ArrayList<User> loggedInUsers = new ArrayList<>();

    public void doLogin() {
        // hier inloggen!

        // check of user bestaat
            // input ophalen uit username-vakje
        String usernameInput = nameTextField.getText();

            // check of username-input een waarde heeft -> als leeg, geef melding
        if(usernameInput.trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Deze gebruikersnaam is niet bekend, probeer het nog eens.");
            alert.show();
            return;
        }

            // check of deze user bestaat in het systeem
                // haal alle info van user op uit database
                //Totdat we een database hebben, gebruiken we een Arraylist met gebruikers
        boolean userNameKnown = false;
        for (User user: Main.userList){
            if(user.getUserName().equals(usernameInput)){
                userNameKnown = true;
            }
        }

        if(!userNameKnown){   //gebruikersnaam is onbekend
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Deze gebruikersnaam is niet bekend, probeer het nog eens.");
            alert.show();
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
        // Controleren of een gebruiker een geldig wachtwoord invoert.

        // Check of deze gelijk zijn -> zo niet -> geef foutmelding
        if (!opgehaaldeWachtwoord.equals(wachtwoordInput)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Het wachtwoord is niet juist, probeer het nog eens.");
            alert.show();
            return;
        }

        // Kijken welke rol iemand heeft
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
                Student student = new Student(userId,userName,userPassword,roleUser);
                loggedInUsers.add(student);
                break;
            default:
                break;
            }

        // Maak een object aan

        Main.getSceneManager().showWelcomeScene();
        // als ze gelijk zijn -> ga door naar inlogschermen

        // bepalen welke inlogscherm getoond moet worden
        // data ophalen uit database (rest van info)
        // gebruikersobject aanmaken (student / docent etc.) -> ? hoe bepalen we dit vanuit database ?
            // toon welkomst-scherm (welcomeScene.fxml)
            // -> deze functionaliteit moet in het welkomst-scherm worden geregeld
            // vul welkomst-scherm met juiste keuzes voor rol van gebruiker
            // checken wat voor user de gebruiker is

        // tonen van juiste inlogscherm
    }

    public void doQuit() {
        // sluit applicatie?
            System.exit(0);
    }

}
