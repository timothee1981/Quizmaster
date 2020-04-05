package database.mysql;

import model.Group;
import model.Teacher;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDAO extends AbstractDAO implements GenericDAO {

    //Toegang tot de Quizmaster database
    public GroupDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    //Specifieke groep ophalen uit de Quizmaster database
    Group group = null;
    public Group getGroupById(int groepId){
        String sql = "SELECT * FROM groep WHERE groepId = ?;";
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
    public void deleteGroup(int groepId) {

        String sql = "DELETE FROM groep WHERE groepId = ?;";
        try {
            PreparedStatement preparedStatement = dBaccess.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, groepId);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlFout) {
            System.out.println(sqlFout.getMessage());
        }
    }

    @Override
    public ArrayList getAll() {
        //todo:
        ArrayList<Group> groupArrayList = new ArrayList<>();
        String sql = "SELECT * FROM groep;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int groepId = resultSet.getInt(1);
                String groepnaam = resultSet.getString(2);
                Teacher teacher = getTeacher(3);
                groupArrayList.add(new Group(groepId, groepnaam, teacher));
            }
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        } return groupArrayList;
    }

    private Teacher getTeacher(int id) {
        UserDAO userDAO = new UserDAO(dBaccess);
        //todo: make this work
        Teacher teacher = (Teacher)userDAO.getOneById(id);
        return teacher;
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
        String sql = "UPDATE groep SET " +
                " groepNaam = ?, userdocentId = ? " +
                "WHERE groepId = ?; ";
        try{
            // sla data van user op
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, group.getGroepnaam());
            preparedStatement.setInt(2, group.getTeacher().getUserId());
            preparedStatement.setInt(3,group.getGroepId());

            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
