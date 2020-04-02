package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Teacher extends User {

    public Teacher(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    public Teacher(){
        this(DEFAULT_USER_ID, DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_ROLE);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        return  menuItems;
    }

}
