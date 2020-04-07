package model;

import java.util.ArrayList;
import java.util.Objects;

public class Course {

    //Constanten
    final public static int DEFAULT_COURSE_ID = 0;

    //Cursus attributen
    private int cursusId;
    private String cursusNaam;
    public int userIdCoordinator;
    public ArrayList<Quiz> quizzes;

    //Cursus constructor
    public Course(String cursusNaam, int userIdCoordinator){
        this.cursusNaam = cursusNaam;
        this.userIdCoordinator = userIdCoordinator;
        this.quizzes = new ArrayList<>();
    }

    //Default constructor, waarbij de default-waarden aangegeven zijn (want in DB als not null)
    public Course() {
        this("cursusNaam", 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return cursusId == course.cursusId &&
                userIdCoordinator == course.userIdCoordinator &&
                cursusNaam.equals(course.cursusNaam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cursusId, cursusNaam, userIdCoordinator);
    }

    //String representatie van Cursus
    @Override
    public String toString() {
        return "" + cursusNaam;
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

    public int getUserIdCoordinator() {
        return userIdCoordinator;
    }

}
