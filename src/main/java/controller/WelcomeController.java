package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {
        // haal de ingelogde gebruiker op en toon de menu-items van deze gebruiker
        ArrayList<MenuItem> menuItems = LoginController.loggedInUsers.get(0).getMenuItems();

        // toon menu items van de gebruiker die ingelogd is
        for (MenuItem menuItem : menuItems) {
            taskMenuButton.getItems().add(menuItem);
        }

        //toon welkom 'username' (role)
        String userName = LoginController.loggedInUsers.get(0).getUserName();
        String userRole = LoginController.loggedInUsers.get(0).getRole();
        String welkomstTekst = String.format("Welkom %s (%s)",userName, userRole);
        welcomeLabel.setText(welkomstTekst);
    }

    @FXML
    public void goToScreenOf(){
    }

    public void doLogout() {
        LoginController.loggedInUsers.remove(0);
        Main.getSceneManager().showLoginScene();
    }
}
