package controller;

import model.Teacher;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCouchDBControllerTest {

    @Test
    void saveUser() {
        Teacher user = new Teacher(1337, "Stefan", "Stefan", "Docent");

        UserCouchDBController userCouchDBController = new UserCouchDBController();
        userCouchDBController.saveUser(user);

    }
}