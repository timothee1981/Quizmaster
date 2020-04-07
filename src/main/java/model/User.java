package model;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.Objects;

public abstract class User implements Comparable<User> {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                userName.equals(user.userName) &&
                password.equals(user.password) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, password, role);
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
        return String.format("%s - %s",userName, role);
    }

    @Override
    public int compareTo(User otherUser) {
        // sorteer op alfabet
        return this.userName.compareTo(otherUser.userName);
    }
}
