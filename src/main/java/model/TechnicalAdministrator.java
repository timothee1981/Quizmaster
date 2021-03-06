package model;

import controller.LoginController;
import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class TechnicalAdministrator extends User {
    public TechnicalAdministrator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem1 = new MenuItem("Gebruikersbeheer");
        menuItem1.setOnAction(actionEvent -> Main.getSceneManager().showManageUserScene());
        menuItems.add(menuItem1);

        return menuItems;
    }

}
