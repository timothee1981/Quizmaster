package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;

    public void doLogin() {
        // hier inloggen!

        // check of user bestaat
            // input ophalen uit username-vakje

            // check of username-input een waarde heeft
            // check of deze user bestaat in het systeem
                // haal alle info van user op uit database
                // als er geen info is opgehaald -> dan bestaat deze user niet
                // -> geef dan foutmelding (neem contact op met technisch beheerder)

        // checken of wachtwoord ingevoerd is gelijk aan wachtwoord user
            // van de opgehaalde user, check of wachtwoord gelijk is aan ingevoerd wachtwoord
            // input ophalen uit wachtwoord vakje
                // haal wachtwoord op, op basis van username
                // check of deze gelijk zijn -> zo niet -> geef foutmelding
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
