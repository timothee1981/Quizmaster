package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Course;
import view.Main;

import java.util.ArrayList;

public class ManageCoursesController {
    @FXML
    public ListView courseList;

    @FXML
    private Button newCourseButton;

    @FXML
    private Button wijzigCursusButton;

    @FXML
    private Button verwijderCursusButton;

    @FXML
    private Button terugNaarMenuButton;

    @FXML
    private ArrayList<Course> courseArrayList = new ArrayList<>();


    //Opvragen van alle cursussen, om ze te tonen in een Listview
    public void setup() {
        courseList.getItems().clear(); //een lege lijst
        courseArrayList = getAllCourses();
        for (Course course : courseArrayList) {
            courseList.getItems().add(course); //cursus toevoegen aan de lijst
        }
    }

    //Opvragen van een lijst van alle cursussen
    public ArrayList<Course> getAllCourses(){
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(),
                DBAccess.getMainUserPassword()); //toegang tot db
        dbAccess.openConnection(); //connectie open
        CourseDAO courseDAO = new CourseDAO(dbAccess); //cursusDAO instantie
        ArrayList<Course> courseArrayList = courseDAO.getAll(); //alle aanwezige items in de lijst laden
        dbAccess.closeConnection(); //connectie sluiten
        return courseArrayList;
    }

    //Ga naar het menu met taken van de Administrator
    public void doMenu(){
        Main.getSceneManager().showWelcomeScene();
    }
    //Toevoegen van een nieuwe cursus
    public void doCreateCourse(){
        Main.getSceneManager().showCreateUpdateCourseScene(new Course());
    }

    //Wijzigen van een cursus(naam en coordinator)
    public void doUpdateCourse(){
        Course course = (Course) courseList.getSelectionModel().getSelectedItem();
        if(course == null){
            showInformationMessage("Selecteer een cursus.");
            return;
        }
        Main.getSceneManager().showCreateUpdateCourseScene(course);
    }

    //Verwijderen van een cursus
    public void doDeleteCourse(){
        // haal geselecteerde gebruiker op
        Course course = (Course)courseList.getSelectionModel().getSelectedItem();
        if(course == null){
            showInformationMessage("Selecteer een cursus.");
            return;
        }

        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        courseDAO.deleteCourse(course);
        dbAccess.closeConnection();

        // toon melding dat groep verwijderd is
        String informationMessage = String.format("Cursus %s is verwijderd.", course.getCursusNaam());
        showInformationMessage(informationMessage);

        // draai setup van pagina nogmaals (page-refresh)
        setup();
        //todo Afstemmen over upon delete restricties? Cursussen niet verwijderen als er groepen/quizzes zijn?
    }

    private void showInformationMessage(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(informationMessage);
        alert.show();
    }

    //Simpele methode, om een test op los te laten om te oefenen met testen
    public int getallenOptellen(int A, int B) {
        return A+B;
    }

}
