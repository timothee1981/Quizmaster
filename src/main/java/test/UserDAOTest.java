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
        User expectedResult = new Teacher(1,"Stefan","Stefan","Docent");
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(),DBAccess.getMainUser(),DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        User actualResult = userDAO.getUserByUsername("Stefan");
        dbAccess.closeConnection();
        assertEquals(expectedResult.toString(),actualResult.toString());
    }
}