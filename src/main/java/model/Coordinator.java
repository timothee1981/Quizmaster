package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Coordinator extends User {

    public Coordinator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Create/read/update/delete quiz"));
        menuItems.add(new MenuItem("Show quiz dashboard"));
        return menuItems;
    }

    //methode maken die voegd een nieuw vraag met een 4 aantwoorden
    //een aantwoord is de goede

    //methode die antoord toevogd aan een bestaande quiz

    //quiz Crud





}
