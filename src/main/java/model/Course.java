package model;

import java.util.ArrayList;

public class Course {

    //Cursus attributen: cursus-id, en cursus-naam. De Administrator kent separaat de coordinator(Id) toe.
    private int cursusId;
    private String cursusNaam;
    public ArrayList<Course> cursusLijst; //Een overzicht met alle cursussen, ter bewerking door Administrator

    //Cursus constructor
    public Course(int cursusId, String cursusNaam){
        this.cursusId = cursusId;
        this.cursusNaam = cursusNaam;
    }

    //Cursus getters en setters. Hiermee kan de Administrator straks cursussen bekijken en bewerken.
    public int getCursusId() {
        return cursusId;
    }

    public void setCursusId(int cursusId) {
        this.cursusId = cursusId;
    }

    public String getCursusNaam() {
        return cursusNaam;
    }

    public void setCursusNaam(String cursusNaam) {
        this.cursusNaam = cursusNaam;
    }

    public ArrayList<Course> getCursusLijst() {
        return cursusLijst;
    }

    public void setCursusLijst(ArrayList<Course> cursusLijst) {
        this.cursusLijst = cursusLijst;
    }
}
