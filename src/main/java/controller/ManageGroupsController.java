package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import model.Group;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class ManageGroupsController {
    private GroupDAO gdao;
    private DBAccess db;
    private ArrayList<Group> groupList = new ArrayList<>(); //Lijst met dummy-groepen


    /*@FXML
    private ListView groupList;*/
    @FXML
    private Button nieuweGroepButton;
    @FXML
    private Button wijzigGroepButton;
    @FXML
    private Button verwijderGroepButton;

    // in dit scherm kan de gebruiker een groep kiezen om aan te passen of te verwijderen.
    // de gebruiker kan ook kiezen om een nieuwe groep aan te maken.

    public void setup() {

        /*// clear list view
        groupList.getItems().clear();

        // get all users
        ArrayList<User> userArrayList = getAllUsers();

        // show users in grid/box-thing
        for(User user:userArrayList) {
            userList.getItems().add(user);*/
        }

        /*this.gdao = new GroupDAO(db.getConnection());
            List<Group> allCustomers = gdao.getAllCustomers();
            for (Customer c : allCustomers) { customerList.getItems().add(c);*/
        // haal de lijst met groepen op

        //In het tekstvak willen we een Arraylist met groepen weergeven
        // ArrayList<Group> groepenLijst =
        //}


    // ga naar het Welkomscherm door op de knop 'menu' te klikken
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    // ga naar het CreateUpdateGroups scherm door op de knop 'nieuw' te klikken
    // er hoeft geen groep uit de lijst geselecteerd te zijn om deze actie te kunnen uitvoeren
    // in het scherm 'naam van de groep' moet (cursief) "voer de naam van de nieuwe groep in" verschijnen
    public void doCreateGroup() {
        Main.getSceneManager().showCreateUpdateGroupScene(new Group());
    }

    // ga naar het CreateUpdateGroups scherm door op de knop 'wijzig' te klikken
    // de gebruiker moet een groep geselecteerd hebben om naar het volgende scherm te kunnen.
    // de methode checkt dit eerst
    // in het scherm 'naam van de groep' moet de naam van de cursus verschijnen
    public void doUpdateGroup() {
        /*Main.getSceneManager().showCreateUpdateGroupScene(Group group);*/
    }

    // de gebruiker selecteert een groep om te verwijderen.
    // indien er geen groep geselecteerd is krijgt de gebruiker een melding 'geen groep geselecteerd'
    // indien er een groep geselecteerd is en de gebruiker klikt op de knop 'verwijder' verschijnt de vraag "weet
    // u zeker dat u groep <naam> wilt verwijderen?"
    // de gebruiker krijgt de keuze 'Ja/Nee'
    // na "ja" maakt de methode een connectie met de database om de gekozen groep uit de tabel te verwijderen.
    public void doDeleteGroup() {}

    //Testgegevens met verzonnen groepen
    /*public void createGroupList() {
        groupList.add(new Group(1, "Basisgroep"));
        groupList.add(new Group(2, "Gevorderdengroep"));
        groupList.add(new Group(3, "Expertgroep"));

    }*/

}
