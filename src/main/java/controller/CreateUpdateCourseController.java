package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.*;
import view.Main;

import java.util.ArrayList;


public class CreateUpdateCourseController {

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

    @FXML
    public TextField courseIdTextbox;


    //Methode voor het prepareren van de pagina en het checken van de user-gegevens en velden
    public void setup(Course course) {
        // CoordinatorKiezen vullen met coordinatoren, coordinatoren ophalen en in een lijst weergeven
        ArrayList<Coordinator> coordinatorArrayList = getCoordinators();
        for (Coordinator coordinator : coordinatorArrayList) {
            CoordinatorKiezen.getItems().add(coordinator);
        }

        // Wil admin nieuwe cursus aanmaken of bestaande cursus updaten? Bestaande cursus laat coordinator zien
        if(! (course.getCursusId() == Course.DEFAULT_COURSE_ID)){
            // bestaande cursus
            CursusAanmakenKnop.setText("Pas de cursus aan");
            CursusnaamTextField.setText(course.getCursusNaam());
            // get coordinator of course:
            Coordinator coordinator = getCoordinatorOfCourse(course.getUserIdCoordinator());
            CoordinatorKiezen.setValue(coordinator);
            courseIdTextbox.setText(String.format("%d",course.getCursusId())); //is dit veld gevuld, dan wijzigen
        } else {
            // nieuwe cursus
            CursusAanmakenKnop.setText("Maak nieuwe cursus");
        }
    }

    private Coordinator getCoordinatorOfCourse(int userIdCoordinator) {
        Coordinator coordinatorToReturn = null;
        // haal alle coordinatoren op
        ArrayList<Coordinator> coordinatorArrayList = getCoordinators();
        // selecteer de juiste
        for(Coordinator coordinator: coordinatorArrayList){
            if(coordinator.getUserId() == userIdCoordinator){
                coordinatorToReturn = coordinator;
            }
        }
        // geef coordinator terug
        return coordinatorToReturn;
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


    @FXML
    public void doCreateUpdateCourse() {
        String cursusNaam = CursusnaamTextField.getText();
        if(cursusNaam.equals("")){
            showInformationMessage("Vul een cursusnaam in.");
            return;
        }

        Coordinator coordinator = (Coordinator) CoordinatorKiezen.getValue();
        if(coordinator == null){
            showInformationMessage("Selecteer een coördinator.");
            return;
        }

        int coordinatorId = coordinator.getUserId();
        Course course = new Course(cursusNaam, coordinatorId);
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(),
                DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dbAccess);

        if (courseIdTextbox.getText().equals("")){
            courseDAO.storeOne(course);
            showInformationMessage("De cursus is aangemaakt");
        } else {
            Course courseToUpdate = new Course(cursusNaam, coordinatorId);
            int intCourseId = Integer.parseInt(courseIdTextbox.getText());
            courseToUpdate.setCursusId(intCourseId);
            courseDAO.updateCourseName(courseToUpdate);
            showInformationMessage("De cursus is geüpdated");
        }
        Main.getSceneManager().showManageCoursesScene();
        dbAccess.closeConnection();
    }

    private void showInformationMessage(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(informationMessage);
        alert.show();
    }

}
