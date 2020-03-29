package unittest;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import model.User;

public class UserUnitTest {

    public static void storeNewUserUnitTest() {
        // creëer variabele voor userobject die in database moeten worden gezet
        String name = "Stefan";
        String password = "Stefan";
        String role = "Docent";

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

    public static void getUserByUsernameUnitTest(){
        // creëer variabele voor userobject die in database moeten worden gezet

        //Creëer dbAccess object
        DBAccess dbAccess = new DBAccess(DBAccess.getDatabaseName(), DBAccess.getMainUser(), DBAccess.getMainUserPassword());
        // maak database-connectie
        dbAccess.openConnection();
        //creëer userDAO instantie
        UserDAO userDAO = new UserDAO(dbAccess);
        // roep save-methode aan
        User user = userDAO.getUserByUsername("Stefan");

        // sluit database connectie
        dbAccess.closeConnection();

        System.out.println(user.getUserId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getRole());

    }
}
