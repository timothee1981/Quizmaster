package model;

import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class Administrator extends User {
    public Administrator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    public Administrator(){
        this(DEFAULT_USER_ID, DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_ROLE);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem1 = new MenuItem("Cursusbeheer");
        menuItem1.setOnAction(actionEvent -> Main.getSceneManager().showManageCoursesScene());
        menuItems.add(menuItem1);

        MenuItem menuItem3 = new MenuItem("Groepenbeheer");
        menuItem3.setOnAction(actionEvent -> Main.getSceneManager().showManageGroupsScene());
        menuItems.add(menuItem3);

        return menuItems;
    }
}
