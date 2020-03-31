package model;

public class Group {

    // Group attributen: id, naam, id van de docent, id van de cursus
    private int groepId;
    private String groepnaam;
    private int userDocentId; // afkomstig van klasse Teacher (?)
    private int cursusId; //afkomstig van de klasse Course


    // constructor
    public Group(int groepId, String groepnaam, int userDocentId, int cursusId) {
        this.groepId = groepId;
        this.groepnaam = groepnaam;
        this.userDocentId = userDocentId;
        this.cursusId = cursusId;
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
}
