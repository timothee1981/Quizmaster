package controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import view.Main;

import java.util.ArrayList;

public class ManageGroupsController {

    @FXML
    private ListView groupList;
    @FXML
    private Button Nieuw;
    @FXML
    private Button Wijzig;
    @FXML
    private Button Verwijder;

    // in dit scherm kan de gebruiker een groep kiezen om aan te passen of te verwijderen.
    // de gebruiker kan ook kiezen om een nieuwe groep aan te maken.

    public void setup() {
        // haal de lijst met groepen op

        //In het tekstvak willen we een Arraylist met groepen weergeven
        // ArrayList<Group> groepenLijst =
        }

    // ga naar het Welkomscherm door op de knop 'menu' te klikken
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    // ga naar het CreateUpdateGroups scherm door op de knop 'nieuw' te klikken
    // er hoeft geen groep uit de lijst geselecteerd te zijn om deze actie te kunnen uitvoeren
    // in het scherm 'naam van de groep' moet (cursief) "voer de naam van de nieuwe groep in" verschijnen
    public void doCreateGroup() {}

    // ga naar het CreateUpdateGroups scherm door op de knop 'wijzig' te klikken
    // de gebruiker moet een groep geselecteerd hebben om naar het volgende scherm te kunnen.
    // de methode checkt dit eerst
    // in het scherm 'naam van de groep' moet de naam van de cursus verschijnen
    public void doUpdateGroup() {}

    // de gebruiker selecteert een groep om te verwijderen.
    // indien er geen groep geselecteerd is krijgt de gebruiker een melding 'geen groep geselecteerd'
    // indien er een groep geselecteerd is en de gebruiker klikt op de knop 'verwijder' verschijnt de vraag "weet
    // u zeker dat u groep <naam> wilt verwijderen?"
    // de gebruiker krijgt de keuze 'Ja/Nee'
    // na "ja" maakt de methode een connectie met de database om de gekozen groep uit de tabel te verwijderen.
    public void doDeleteGroup() {}

}
