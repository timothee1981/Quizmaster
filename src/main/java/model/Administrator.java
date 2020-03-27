package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Administrator extends User {
    public Administrator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Cursusbeheer"));
        menuItems.add(new MenuItem("Maak/wijzig cursus"));
        menuItems.add(new MenuItem("Groepenbeheer"));
        menuItems.add(new MenuItem("Maak/wijzig groep"));
        return menuItems;
    }
}
