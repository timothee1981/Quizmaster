package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public abstract class User {
    private int userId;
    private String userName;
    private String password;
    private String role;

    public User(int userId, String userName, String password, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    //get menuItems class die moet teruggeven menu item
    public abstract ArrayList<MenuItem> getMenuItems();



}
