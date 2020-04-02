package database.mysql;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO extends AbstractDAO implements GenericDAO {

    public ArrayList<Course> cursusLijst;

    //Toegang tot de Quizmaster database
    public CourseDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    //Alle cursussen in een lijst
    @Override
    public ArrayList getAll() {
        ArrayList<Course> cursusLijst = new ArrayList<>();
        return cursusLijst;
    }

    //Een specifieke cursus opvragen op basis van zijn cursusId
    @Override
    public Object getOneById(int id) {
        return null;
    }

    //Een specifieke cursus in database wegschrijven
    @Override
    public void storeOne(Object type) {
    }

    //CursusLijst ophalen uit database
    public ArrayList<Course> getCourses(){
        String sql = "SELECT * FROM course;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int cursusId = resultSet.getInt(1);
                String cursusNaam = resultSet.getString(2);
            }
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        } return getCourses();
    }


    //Specifieke cursus ophalen uit de Quizmaster database
    Course course = null;
    public Course getCourseById(int cursusId){
        String sql = "SELECT * FROM course WHERE cursusID = ?";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, cursusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cursusId = resultSet.getInt(1);
                String cursusNaam = resultSet.getString(2);
            }
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        } return course;
    }

    //Cursus wegschrijven in Quizmaster database
    public void storeNewCourse(int cursusId, String cursusNaam, int userIdCoordinator){
        String sql = "INSERT INTO cursus (cursusId, cursusNaam, userIdCoordinator) VALUES (?,?,?);";
        try{
            UserDAO userDAO = new UserDAO(dBaccess);
            PreparedStatement preparedStatement = dBaccess.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, cursusId);
            preparedStatement.setString(2, cursusNaam);
            preparedStatement.setInt(3, userDAO.getRoleIdByName("Coordinator"));
            preparedStatement.executeUpdate();
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        }
    }

}
