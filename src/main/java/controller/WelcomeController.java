package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.Student;
import view.Main;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {
        // haal de lijst met ingelogde gebruikers op

        // bepalen wat voor rol de gebruiker heeft
        if (LoginController.loggedInUsers.get(0) instanceof Student) {
            // toon menu items van Student
            MenuItem item1 = new MenuItem("Selecteer quiz");
            item1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Main.getSceneManager().showSelectQuizForStudent();
                }
            });
            taskMenuButton.getItems().add(item1);
        }
        // haal de takenlijst op die bij die rol hoort
            // lijst per user
        // drop down menu vullen met taken uit de takenlijst
        // als de gebruiker een taak kiest, moet hij naar juiste pagina worden verwezen

        //taken in drop down menu zetten
           /* MenuItem item1 = new MenuItem("Selecteer quiz");
            item1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Main.getSceneManager().showSelectQuizForStudent();
                }
            });
            taskMenuButton.getItems().add(item1);*/


        // check wat voor gebruiker de gebruiker is
        // vul dropdown met juiste opties
    }

    public void doLogout() {}
}
