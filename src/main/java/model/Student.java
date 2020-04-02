package model;

import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class Student extends User {
    public Student(int userId, String userName, String password, String role) {
        super(userId, userName, password, role);
    }

    //De dingen die een student kan doen komen hier
    @Override
    public ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem1 = new MenuItem("Inschrijven/uitschrijven cursus");
        menuItem1.setOnAction(actionEvent -> Main.getSceneManager().showStudentSignInOutScene());
        menuItems.add(menuItem1);

        MenuItem menuItem2 = new MenuItem("Selecteer quiz");
        menuItem2.setOnAction(actionEvent -> Main.getSceneManager().showSelectQuizForStudent());
        menuItems.add(menuItem2);

        MenuItem menuItem3 = new MenuItem("Quiz invullen");
        menuItem3.setOnAction(actionEvent -> Main.getSceneManager().showSelectQuizForStudent());
        menuItems.add(menuItem3);

        return menuItems;
    }
}
