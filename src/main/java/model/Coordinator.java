package model;

import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class Coordinator extends User {

    public Coordinator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    public Coordinator(){
        this(DEFAULT_USER_ID, DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_ROLE);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem2 = new MenuItem("Toon dashboard");
        menuItems.add(menuItem2);
        menuItem2.setOnAction(actionEvent -> Main.getSceneManager().showCoordinatorDashboard());

        MenuItem menuItem3 = new MenuItem("Manage quizes");
        menuItems.add(menuItem3);
        menuItem3.setOnAction(actionEvent -> Main.getSceneManager().showManageQuizScene());

        return menuItems;
    }
}
