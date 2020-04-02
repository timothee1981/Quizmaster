package model;

import java.util.ArrayList;

public class Group {

    // Group attributen: id, naam, id van de docent, id van de cursus
    private int groepId;
    private String groepnaam;
    public ArrayList<Group> groepenLijst; // overzicht van alle groepen


    // constructor
    public Group(int groepId, String groepnaam) {
        this.groepId = groepId;
        this.groepnaam = groepnaam;
    }

    //Default constructor, waarbij de default-waarden aangegeven zijn (want in DB als not null)
    public Group(){
        this(0,"groepnaam");
    }

    // getters en setters
    public int getGroepId() {
        return groepId;
    }

    public void setGroepId(int groepId) {
        this.groepId = groepId;
    }

    public String getGroepnaam() {
        return groepnaam;
    }

    public void setGroepnaam(String groepnaam) {
        this.groepnaam = groepnaam;
    }

    public ArrayList<Group> getGroepenLijst() {
        return groepenLijst;
    }

    public void setGroepenLijst(ArrayList<Group> groepenLijst) {
        this.groepenLijst = groepenLijst;
    }
}
