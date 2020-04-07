package controller;

import model.Group;
import model.Teacher;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ManageGroupsControllerTest {

    @Test
    void doCreateGroup() {
        ManageGroupsController manageGroupsController = new ManageGroupsController();
        Teacher docent = new Teacher();
        Group testGroup = new Group(345,"Groepje", docent);
        manageGroupsController.doCreateGroup();
    }
}