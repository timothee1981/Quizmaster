package database.mysql;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends AbstractDAO implements GenericDAO {

    public UserDAO(DBAccess dbAccess){super(dbAccess);}

    /**
     * We gaan er van uit dat:
     * - bij het aanroepen van deze methode:
     * -- er een open database connectie open is
     * - bij het eindigen van deze methode:
     * -- de databaseconnectie open is
     */
    public void storeNewUser(String name, String password, String role ){
        String sql = "INSERT INTO gebruiker ( gebruikersnaam, wachtwoord) VALUES (?,?);";

        try{
            // sla data van user op
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            // sla key van gebruiker op
            int userId = executeInsertPreparedStatement(preparedStatement);

            // zoek de Id op van de rol die bij user hoort
            int roleId = getRoleIdByName(role);

            // vul de gebruikers-rol tabel met de userId en de Id van de bijbehorende rol
            storeUserRole(userId, roleId);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(int userId, String username, String password, String role){

        String sql = "UPDATE gebruiker SET " +
                " gebruikersnaam = ?, wachtwoord = ? " +
                "WHERE userId = ?; ";

        try{
            // sla data van user op
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3,userId);

            executeManipulatePreparedStatement(preparedStatement);

            // zoek de Id op van de rol die bij user hoort
            int roleId = getRoleIdByName(role);

            // vul de gebruikers-rol tabel met de userId en de Id van de bijbehorende rol
            updateUserRole(userId, roleId);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void updateUserRole(int userId, int roleId) {

        String sql = "UPDATE gebruikerrol SET " +
                "roleId = ? " +
                "WHERE userId = ? ;";
        try{
            PreparedStatement preparedStatement = dBaccess.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, roleId );
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void storeUserRole(int userId, int roleId) {
        String sql = "INSERT INTO gebruikerrol (userId, roleId) VALUES (?,?);";
        try{
            PreparedStatement preparedStatement = dBaccess.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userId );
            preparedStatement.setInt(2, roleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int getRoleIdByName(String role) {
        int roleId = 0;
        String sql = "SELECT * FROM rol WHERE rol_beschrijving = ?";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, role);

            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()) {
                roleId = resultset.getInt(1);
            }
        } catch (SQLException e){
        System.out.println(e.getMessage());
        }
        return roleId;
    }

    @Override
    public ArrayList<User> getAll() {

        // haal user-info op
        String sql = "SELECT gebruiker.userId, gebruiker.gebruikersnaam, gebruiker.wachtwoord, rol.rol_beschrijving" +
                " FROM gebruiker" +
                " JOIN gebruikerrol ON gebruiker.userId = gebruikerrol.userId" +
                " JOIN rol ON gebruikerrol.roleId = rol.rolId ;";

        User user = null;
        ArrayList<User> userList = new ArrayList<>();

        try{
            PreparedStatement preparedStatement = getStatement(sql);
            
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()){

                int userId = resultset.getInt(1);
                String username = resultset.getString(2) ;
                String password = resultset.getString(3);
                String role = resultset.getString(4);

                // afhankelijk van de rol, creëer en return het juiste object
                switch (role){
                    case "Student":
                        user = new Student(userId, username, password, role);
                        break;
                    case "Docent":
                        user = new Teacher(userId, username, password, role);
                        break;
                    case "Coordinator":
                        user = new Coordinator(userId, username, password, role);
                        break;
                    case "Administrator":
                        user = new Administrator(userId, username, password, role);
                        break;
                    case "Technisch beheerder":
                        user = new TechnicalAdministrator(userId, username, password, role);
                        break;
                    default:
                        user = null;
                        break;
                }
                userList.add(user);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return userList;
    }

    @Override
    public Object getOneById(int id) {

        // haal user-info op
        String sql = "SELECT gebruiker.userId, gebruiker.gebruikersnaam, gebruiker.wachtwoord, rol.rol_beschrijving" +
                " FROM gebruiker" +
                " JOIN gebruikerrol ON gebruiker.userId = gebruikerrol.userId" +
                " JOIN rol ON gebruikerrol.roleId = rol.rolId" +
                " WHERE gebruiker.userId = ?";
        User user = null;

        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()){
                int userId = resultset.getInt(1);
                String username = resultset.getString(2) ;
                String password = resultset.getString(3);
                String role = resultset.getString(4);

                // afhankelijk van de rol, creëer en return het juiste object
                switch (role){
                    case "Student":
                        user = new Student(userId, username, password, role);
                        break;
                    case "Docent":
                        user = new Teacher(userId, username, password, role);
                        break;
                    case "Coordinator":
                        user = new Coordinator(userId, username, password, role);
                        break;
                    case "Administrator":
                        user = new Administrator(userId, username, password, role);
                        break;
                    case "Technisch beheerder":
                        user = new TechnicalAdministrator(userId, username, password, role);
                        break;
                    default:
                        user = null;
                        break;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public void storeOne(Object type) {
        // Id is auto-increment dus Id moet niet meegegeven worden bij het opslaan
        User user = (User) type;

        String username = user.getUserName();
        String password = user.getPassword();
        String role = user.getRole();
        storeNewUser(username, password, role);
    }

    public User getUserByUsername(String usernameInput){
        // haal user-info op
        String sql = "SELECT gebruiker.userId, gebruiker.gebruikersnaam, gebruiker.wachtwoord, rol.rol_beschrijving" +
                " FROM gebruiker" +
                " JOIN gebruikerrol ON gebruiker.userId = gebruikerrol.userId" +
                " JOIN rol ON gebruikerrol.roleId = rol.rolId" +
                " WHERE gebruikersnaam = ?";
        User user = null;

        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, usernameInput);

            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()){

                int userId = resultset.getInt(1);
                String username = resultset.getString(2) ;
                String password = resultset.getString(3);
                String role = resultset.getString(4);

                // afhankelijk van de rol, creëer en return het juiste object
                switch (role){
                    case "Student":
                        user = new Student(userId, username, password, role);
                        break;
                    case "Docent":
                        user = new Teacher(userId, username, password, role);
                        break;
                    case "Coordinator":
                        user = new Coordinator(userId, username, password, role);
                        break;
                    case "Administrator":
                        user = new Administrator(userId, username, password, role);
                        break;
                    case "Technisch beheerder":
                        user = new TechnicalAdministrator(userId, username, password, role);
                        break;
                    default:
                        user = null;
                        break;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }

    public void deleteUser(User user){

        String sql = "DELETE FROM gebruiker WHERE userId = ?";

        try{
            //verwijdere eerst de rol-referentie
            // zoek de Id op van de rol die bij user hoort
            int roleId = getRoleIdByName(user.getRole());

            // vul de gebruikers-rol tabel met de userId en de Id van de bijbehorende rol
            deleteUserRole(user.getUserId());

            // verwijder daarna de user-recordp
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(3,user.getUserId());

            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteUserRole(int userId) {

        String sql = "DELETE FROM gebruikerrol WHERE userId = ?";

        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, userId);
            executeManipulatePreparedStatement(preparedStatement);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
