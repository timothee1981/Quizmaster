package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Student extends User {
    public Student(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    //De dingen die een student kan doen komen hier
    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Inschrijven/uitschrijven cursus"));
        menuItems.add(new MenuItem("Selecteer quiz"));
        menuItems.add(new MenuItem("Quiz invullen"));
        return menuItems;
    }

}
