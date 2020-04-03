package model;

import java.util.ArrayList;

public class Group {

    // Group attributen: id, naam, id van de docent, id van de cursus
    final public static int DEFAULT_GROUP_ID = 0;

    private int groepId;
    private String groepnaam;
    private Teacher teacher;
    public ArrayList<Group> groepenLijst; // overzicht van alle groepen


    // constructor
    public Group(int groepId, String groepnaam, Teacher teacher) {
        this.groepId = groepId;
        this.groepnaam = groepnaam;
        this.teacher = teacher;
    }

    //Default constructor, waarbij de default-waarden aangegeven zijn (want in DB als not null)
    public Group(){
        this(DEFAULT_GROUP_ID,"groepnaam", new Teacher());
    }
    //todo: remove magic numbers

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setGroepenLijst(ArrayList<Group> groepenLijst) {
        this.groepenLijst = groepenLijst;
    }
}
