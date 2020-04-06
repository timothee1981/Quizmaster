package model;

import java.util.ArrayList;

public class Course {

    //Constanten
    final public static int DEFAULT_COURSE_ID = 0;

    //Cursus attributen
    private int cursusId;
    private String cursusNaam;
    public int userIdCoordinator;

    //Cursus constructor
    public Course(String cursusNaam, int userIdCoordinator){
        this.cursusNaam = cursusNaam;
        this.userIdCoordinator = userIdCoordinator;
    }

    //Default constructor, waarbij de default-waarden aangegeven zijn (want in DB als not null)
    public Course() {
        this("cursusNaam", 0);
    }

    //String representatie van Cursus
    @Override
    public String toString() {
    StringBuilder courseToString = new StringBuilder("");
    courseToString.append(cursusId).append(" ");
    courseToString.append(cursusNaam).append(" ");
    if (userIdCoordinator != 0){
        courseToString.append(userIdCoordinator).append(".");
    }
    return courseToString.toString();
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

    public int getUserIdCoordinator() { return userIdCoordinator; }


}
