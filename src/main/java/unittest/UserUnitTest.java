package unittest;

import database.mysql.DBAccess;
import database.mysql.UserDAO;

public class UserUnitTest {

    public void StoreNewUserUnitTest() {
        // creëer variabele voor userobject die in database moeten worden gezet
        String name = "Stefan";
        String password = "Stefan";
        String role = "Teacher";

        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        userDAO.storeNewUser(name, password, role);
        // sluit database connectie
        dbAccess.closeConnection();
    }
}
