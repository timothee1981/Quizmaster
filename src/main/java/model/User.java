package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public abstract class User {

    final static public int DEFAULT_USER_ID = -1;
    final static protected String DEFAULT_USERNAME = "";
    final static protected String DEFAULT_PASSWORD = "";
    final static protected String DEFAULT_ROLE = "";

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

    public User(){
        this(DEFAULT_USER_ID, DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_ROLE);
    }

    //get menuItems class die moet teruggeven menu item
    public abstract ArrayList<MenuItem> getMenuItems();

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("userId: %d\tuserName: %s\t role: %s",userId, userName, role);
    }
}
