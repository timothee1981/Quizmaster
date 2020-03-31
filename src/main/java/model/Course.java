package model;

import java.util.ArrayList;

public class Course {

    //Cursus attributen: id, naam en de userID van de cursuscoördinator
    private int cursusId;
    private String cursusNaam;
    private int cursusCoordinatorId; //Deze 'krijgt' de cursus van de User-Class
    public ArrayList<Course> cursusLijst; //Een overzicht met alle cursussen, ter bewerking door Administrator


    //Cursus constructor
    public Course(int cursusId, String cursusNaam, int cursusCoordinatorId){
        this.cursusId = cursusId;
        this.cursusNaam = cursusNaam;
        this.cursusCoordinatorId = cursusCoordinatorId;
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
