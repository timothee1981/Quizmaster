package database.mysql;

import model.Course;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/*In deze test vergelijk ik de cursus die uit de database wordt gehaald, met de cursus op een bepaalde plek in de db*/

class CourseDAOTest {

    @Test
    void getOneById() {
        DBAccess dbAccess;
        dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        Course proefkonijnCursus = (Course) courseDAO.getOneById(5);
        dbAccess.closeConnection();
        Course verwachteCursus = new Course("DinsdagCursus", 5);
        verwachteCursus.setCursusId(5);
        Assert.assertEquals(proefkonijnCursus, verwachteCursus);
    }
}