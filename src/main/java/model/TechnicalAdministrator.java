package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class TechnicalAdministrator extends User {
    public TechnicalAdministrator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Gebruikersbeheer"));
        menuItems.add(new MenuItem("Maak/wijzig gebruikers"));
        return menuItems;
    }
}
