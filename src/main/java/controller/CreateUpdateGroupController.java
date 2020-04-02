package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import model.Course;
import model.Group;
import view.Main;

import java.util.ArrayList;


public class CreateUpdateGroupController {
    private Group group;
    private ArrayList<Group> groupList = new ArrayList<>(); //Lijst met dummy-groepen


    public void setup(Group group) {}

    // ga naar het Welkomscherm door op de knop 'menu' te klikken

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    // ga naar het scherm Groepenbeheer door op de knop 'Terug naar Groepenbeheer' te klikken
    public void doTerugNaarGroepenbeheer() {
        Main.getSceneManager().showManageGroupsScene();
    }

    // ga naar het scherm Cursusbeheer door op de knop 'Naar Cursusbeheer' te klikken
    public void doNaarCursusbeheer() {
            Main.getSceneManager().showManageCoursesScene();
    }

    public void doCreateUpdateGroup() {}

    //Testgegevens met verzonnen groepen
    public void createGroupList() {
        groupList.add(new Group(1, "Basisgroep"));
        groupList.add(new Group(2, "Gevorderdengroep"));
        groupList.add(new Group(3, "Expertgroep"));

    }

}
