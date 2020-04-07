package database.mysql;

import model.Course;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {

    @Test
    void getOneById() {
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(),
                DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        Course proefkonijnCursus = (Course) courseDAO.getOneById(1); //De in de test op te halen cursus
        dbAccess.closeConnection();

        Course verwachteCursus = new Course("WeekendCursus", 5); //Wat ik verwacht te krijgen
        verwachteCursus.setCursusId(1); //id is auto-increment

        Assert.assertEquals(proefkonijnCursus, verwachteCursus); //De daadwerkelijke vergelijking
    }
}