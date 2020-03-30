package test;

import com.mysql.cj.xdevapi.DbDoc;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import model.Teacher;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void getUserByUsername() {
        //TODO zorgen dat dit werkt: krijgt bij draaien test een error
        //TODO: bij draaien van applicatie gaat het goed, bij draaien van test krijg ik een nullpointerExceptie
        User expectedResult = new Teacher(1,"Stefan","Stefan","Docent");
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(),DBAccess.getMainUser(),DBAccess.getMainUserPassword());
        UserDAO userDAO = new UserDAO(dbAccess);
        User actualResult = userDAO.getUserByUsername("Stefan");
        assertEquals(expectedResult, actualResult);
    }
}