package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.*;
import view.Main;

import java.util.ArrayList;


public class CreateUpdateCourseController {

    private Course course; //Een cursusobject
    private ArrayList<Course> courseList = new ArrayList<>(); //Lijst met dummy-cursussen

    //Verwijzingen naar de invulvelden en knoppen in de View
    @FXML
    private TextField CursusnaamTextField;

    @FXML
    private ComboBox CoordinatorKiezen;

    @FXML
    private Button CursusAanmakenKnop;

    @FXML
    private Button NaarCursusbeheerKnop;

    @FXML
    private Button NaarMenuKnop;


    //Methode voor het prepareren van de pagina en het checken van de user-gegevens en velden
    public void setup(Course course) {
        // CoordinatorKiezen vullen met coordinatoren, coordinatoren ophalen en in een lijst weergeven
        ArrayList<Coordinator> coordinatorArrayList = getCoordinators();
        for (Coordinator coordinator : coordinatorArrayList) {
            CoordinatorKiezen.getItems().add(coordinator);
        }

        // Wil admin nieuwe cursus aanmaken of bestaande cursus updaten? Bestaande cursus laat coordinator zien
        if(! (course.getCursusId() == Course.DEFAULT_COURSE_ID)){
            CursusnaamTextField.setText(course.getCursusNaam());
            CoordinatorKiezen.setValue(course.getUserIdCoordinator());
        }
    }

    //Alle gebruikers ophalen
    private ArrayList<User> getUsers(){
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        ArrayList<User> userList = userDAO.getAll();
        dbAccess.closeConnection();
        return userList;
    }

    //De coordinatoren eruitfilteren
    private ArrayList<Coordinator> getCoordinators() {
        ArrayList<User> userList = getUsers();
        ArrayList<Coordinator> coordinatorArrayList = new ArrayList<>();
        for (User user: userList) {
            if (user instanceof Coordinator) {
                coordinatorArrayList.add((Coordinator) user);
            }
        } return coordinatorArrayList;
    }

    //Gaat terug naar het keuzemenu van de administrator
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    //Gaat naar het Cursusbeheerscherm
    public void manageCourses(){
            Main.getSceneManager().showManageCoursesScene();
        }

    //Maak een cursus aan in de db, en later, pas een cursus aan in de db
    @FXML
    public void doCreateUpdateCourse(ActionEvent actionEvent) {
        String cursusNaam = CursusnaamTextField.getText();
        Coordinator coordinator = (Coordinator) CoordinatorKiezen.getValue();
        int coordinatorId = coordinator.getUserId();
        course = new Course(cursusNaam,coordinatorId);
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword()); //toegang tot db
        dbAccess.openConnection(); //connectie openen
        CourseDAO courseDAO = new CourseDAO(dbAccess); //CursusDAO instantieren
        if (course != null){
            courseDAO.storeOne(course);
        } else
            System.out.println("Geen cursus aangemaakt");
        dbAccess.closeConnection(); //connectie sluiten
    }

}
