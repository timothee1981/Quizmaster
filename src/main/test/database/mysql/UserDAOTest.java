package database.mysql;

import model.Teacher;
import model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void getOneById() {
        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer groupDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep delete-methode aan
        Teacher userToTest = (Teacher)(User)userDAO.getOneById(1);
        // sluit database connectie
        dbAccess.closeConnection();

        User userExpected = new Teacher(1,"Stefan","Stefan","Docent");

        Assert.assertEquals(userToTest, userExpected);
    }
}