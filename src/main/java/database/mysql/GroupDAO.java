package database.mysql;

import model.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDAO extends AbstractDAO implements GenericDAO {

    //Toegang tot de Quizmaster database
    public GroupDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    // Groepenlijst ophalen uit database
    public ArrayList<Group> getGroups(){
        String sql = "SELECT * FROM course;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int groepId = resultSet.getInt(1);
                String groepnaam = resultSet.getString(2);
            }
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        } return getGroups();
    }

    //Specifieke groep ophalen uit de Quizmaster database
    Group group = null;
    public Group getGroupById(int groepId){
        String sql = "SELECT * FROM groep WHERE groepId = ?";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, groepId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                groepId = resultSet.getInt(1);
                String groepnaam = resultSet.getString(2);
            }
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        } return group;
    }

    // Groep verwijderen uit Quizmaster database
    public void deleteGroup(int groepId, String groepnaam) {

        //todo delete * where groupId = ?
        String sql = "DELETE FROM group (groepId, groepnaam) VALUES (?,?);";
        try {


            PreparedStatement preparedStatement = dBaccess.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, groepId);
            preparedStatement.setString(2, groepnaam);
            preparedStatement.executeUpdate();


        } catch (SQLException sqlFout) {
            System.out.println(sqlFout.getMessage());
        }
    }

    @Override
    public ArrayList getAll() {
        //todo:
        String sql = "SELECT * FROM course;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int groepId = resultSet.getInt(1);
                String groepnaam = resultSet.getString(2);
            }
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        } return getGroups();
    }

    @Override
    public Object getOneById(int id) {
        return null;
    }

    @Override
    public void storeOne(Object groupObject) {

        String sql = "INSERT INTO groep (groepNaam, userdocentId) VALUES (?,?);";
        try{
            // zet inkomend object om in een group
            Group group = (Group)groupObject;

            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, group.getGroepnaam());
            preparedStatement.setString(2, String.valueOf(group.getTeacher().getUserId()));
            preparedStatement.executeUpdate();

        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        }
    }

    public void updateOne(Group group){
        //todo: implement
    }
}
