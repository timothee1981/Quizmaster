package database.mysql;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO extends AbstractDAO {

    //Toegang tot de Quizmaster database
    public CourseDAO(DBAccess dbAccess){
        super(dbAccess);
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
    public void storeNewCourse(int cursusId, String cursusNaam){
        String sql = "INSERT INTO course (cursusId, cursusNaam) VALUES (?,?);";
        try{
            PreparedStatement preparedStatement = dBaccess.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, cursusId);
            preparedStatement.setString(2, cursusNaam);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlFout){
            System.out.println(sqlFout.getMessage());
        }
    }

}
