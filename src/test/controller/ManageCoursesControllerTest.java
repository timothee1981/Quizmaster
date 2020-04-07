package controller;

import javafx.scene.control.Alert;
import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/*In deze test ga ik controleren of de app de juiste cursus ophaalt uit de database.*/

class ManageCoursesControllerTest {

    private Course course;
    private String CursusNaam = "Beginnerscursus";
    String informationMessage = String.format("Cursus %s is verwijderd.", course.getCursusNaam());

    @Test
    private void showInformationMessage(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(informationMessage);
        alert.show();
    }


    public String getCursusNaam() {
        return CursusNaam;
    }

}