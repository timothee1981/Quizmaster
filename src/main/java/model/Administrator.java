package model;

import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class Administrator extends User {
    public Administrator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem1 = new MenuItem("Cursusbeheer");
        menuItem1.setOnAction(actionEvent -> Main.getSceneManager().showManageCoursesScene());
        menuItems.add(menuItem1);

        MenuItem menuItem2 = new MenuItem("Maak/wijzig cursus");
        menuItem2.setOnAction(actionEvent -> Main.getSceneManager().showCreateUpdateCourseScene(new Course()));
        menuItems.add(menuItem2);

        MenuItem menuItem3 = new MenuItem("Groepenbeheer");
        menuItem3.setOnAction(actionEvent -> Main.getSceneManager().showManageGroupsScene());
        menuItems.add(menuItem3);

        MenuItem menuItem4 = new MenuItem("Maak/wijzig groep");
        menuItem4.setOnAction(actionEvent -> Main.getSceneManager().showCreateUpdateGroupScene(new Group()));
        menuItems.add(menuItem4);

        return menuItems;
    }
}
