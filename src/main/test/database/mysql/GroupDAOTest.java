package database.mysql;

import model.Group;
import model.Teacher;
import model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupDAOTest {

    @Test
    void getGroupById() {
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        dbAccess.openConnection();
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        Group testGroup = (Group)groupDAO.getGroupById(1);
        dbAccess.closeConnection();

        Group expected = new Group(1, "Basis", new Teacher(1, "Stefan", "Stefan", "Docent"));
        Assert.assertEquals(expected, testGroup);

    }
}