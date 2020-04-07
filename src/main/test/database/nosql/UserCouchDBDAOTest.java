package database.nosql;

import CouchDBControllers.UserCouchDBController;
import model.Teacher;
import model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCouchDBDAOTest {

    @Test
    void getUserByDocId() {

        Teacher expectedTeacher = new Teacher(1337, "Stefan", "Stefan", "Docent");

        UserCouchDBController userCouchDBController = new UserCouchDBController();
        User user = userCouchDBController.getUserByDocId("f49384f17de34acc9ea63e87f298eed6");
        Teacher actualTeacher = (Teacher)user;

        Assert.assertEquals(expectedTeacher,actualTeacher);

    }
}