package model;

import java.util.ArrayList;
import java.util.Objects;

public class Group {

    // Group attributen: id, naam, id van de docent, id van de cursus
    final public static int DEFAULT_GROUP_ID = 0;
    final public static String DEFAULT_GROUP_NAME = "groepnaam";

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
        this(DEFAULT_GROUP_ID,DEFAULT_GROUP_NAME, new Teacher());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groepId == group.groepId &&
                groepnaam.equals(group.groepnaam) &&
                Objects.equals(teacher, group.teacher) &&
                Objects.equals(groepenLijst, group.groepenLijst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groepId, groepnaam, teacher, groepenLijst);
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setGroepenLijst(ArrayList<Group> groepenLijst) {
        this.groepenLijst = groepenLijst;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return String.format("%s", groepnaam);
    }
}

