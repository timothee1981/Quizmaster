package database.mysql;

import model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO {

    public UserDAO(DBAccess dbAccess){super(dbAccess);}

    /**
     * We gaan er van uit dat:
     * - bij het aanroepen van deze methode:
     * -- er een open database connectie open is
     * - bij het eindigen van deze methode:
     * -- de databaseconnectie open is
     */
    public void storeNewUser(String name, String password, String role ){
        String sql = "INSERT INTO user ( username, password, role ) VALUES (?,?,?);";

        try{
            PreparedStatement preparedStatement = dBaccess.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
