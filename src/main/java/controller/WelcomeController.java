package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.Student;
import view.Main;

import java.util.ArrayList;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {
        // haal de lijst met ingelogde gebruikers op

        //Hier willen we een Arraylist met menu-items per gebruiker weergeven
        ArrayList<MenuItem> menuItems = LoginController.loggedInUsers.get(0).getMenuItems();

            // toon menu items van de gebruiker die ingelogd is
            for(MenuItem menuItem: menuItems){
                taskMenuButton.getItems().add(menuItem);
            }

            /*item1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Main.getSceneManager().showSelectQuizForStudent();
                }
            });
            */
            //todo: Zorgen dat als je een menu-item aanklikt, dat de app naar die view navigeert
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


    public void doLogout() {
        Main.getSceneManager().showLoginScene();
    }

}
