package model;

public class Group {

    // Group attributen: id, naam, id van de docent, id van de cursus
    private int groepId;
    private String groepnaam;


    // constructor
    public Group(int groepId, String groepnaam) {
        this.groepId = groepId;
        this.groepnaam = groepnaam;
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
