package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import view.Main;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {

            MenuItem item1 = new MenuItem("Select quiz");
            item1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Main.getSceneManager().showSelectQuizForStudent();
                }
            });
            taskMenuButton.getItems().add(item1);


        // check wat voor gebruiker de gebruiker is
        // vul dropdown met juiste opties
    }

    public void doLogout() {}
}
