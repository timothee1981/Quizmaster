package model;

import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class Coordinator extends User {

    public Coordinator(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem1 = new MenuItem("Create/read/update/delete question");
        menuItems.add(menuItem1);
        menuItem1.setOnAction(actionEvent -> Main.getSceneManager().showManageQuestionsScene());

        MenuItem menuItem2 = new MenuItem("Show quiz dashboard");
        menuItems.add(menuItem2);
        menuItem2.setOnAction(actionEvent -> Main.getSceneManager().showCoordinatorDashboard());

        return menuItems;
    }
}
