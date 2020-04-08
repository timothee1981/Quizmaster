package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import model.Group;
import model.Question;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class ManageGroupsController {
    @FXML
    public ListView groupListView;
    @FXML
    private Button nieuweGroepButton;
    @FXML
    private Button wijzigGroepButton;
    @FXML
    private Button verwijderGroepButton;

    // in dit scherm kan de gebruiker een groep kiezen om aan te passen of te verwijderen.
    // de gebruiker kan ook kiezen om een nieuwe groep aan te maken.

    public void setup() {

        // clear list view
        groupListView.getItems().clear();

        // get all groups
        ArrayList<Group> groupArrayList = getAllGroups();

        // show groups in grid/box-thing
        for (Group group : groupArrayList) {
            groupListView.getItems().add(group);
        }
    }

    private ArrayList<Group> getAllGroups() {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        // roep save-methode aan
        ArrayList<Group> groupArrayList = groupDAO.getAll();
        // sluit database connectie
        dbAccess.closeConnection();

        return groupArrayList;
    }

    // ga naar het Welkomscherm door op de knop 'menu' te klikken
    public void doMenu(){
        Main.getSceneManager().showWelcomeScene();
    }

    // ga naar het CreateUpdateGroups scherm door op de knop 'nieuw' te klikken
    // er hoeft geen groep uit de lijst geselecteerd te zijn om deze actie te kunnen uitvoeren
    public void doCreateGroup() {
        Main.getSceneManager().showCreateUpdateGroupScene(new Group());
    }

    // ga naar het CreateUpdateGroups scherm door op de knop 'wijzig' te klikken
    // de gebruiker moet een groep geselecteerd hebben om naar het volgende scherm te kunnen.
    // de methode checkt dit eerst
    // in het scherm 'naam van de groep' moet de naam van de cursus verschijnen
    public void doUpdateGroup() {
        Group group = (Group)groupListView.getSelectionModel().getSelectedItem();
        if(group == null){
            showInformationMessage("Er is geen groep geselecteerd om te wijzigen.");
            return;
        }
        Main.getSceneManager().showCreateUpdateGroupScene(group);
    }

    // de gebruiker selecteert een groep om te verwijderen.
    // Na klikken op 'verwijder'maakt de methode een connectie met de database om de gekozen groep uit de tabel te
    // verwijderen.
    // er verschijnt een melding dat de geselecteerde groep verwijderd is.
    // de verwijderde groep is niet meer zichtbaar in de lijst met groepen.
    public void doDeleteGroup() {
        // haal geselecteerde groep op
        Group group = (Group)groupListView.getSelectionModel().getSelectedItem();

        if(group == null){
            showInformationMessage("Er is geen groep geselecteerd om te verwijderen.");
            return;
        }

        // delete groep
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer groupDAO instantie
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        // roep delete-methode aan
        groupDAO.deleteGroup(group.getGroepId());
        // sluit database connectie
        dbAccess.closeConnection();

        // toon melding dat groep verwijderd is
        String informationMessage = String.format("De groep met de groepnaam: %s is verwijderd.", group.getGroepnaam());
        showInformationMessage(informationMessage);

        // draai setup van pagina nogmaals (page-refresh)
        setup();
    }

    private void showInformationMessage(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(informationMessage);
        alert.show();
    }

}
