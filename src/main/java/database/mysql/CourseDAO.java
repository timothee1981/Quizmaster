package database.mysql;

import model.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO extends AbstractDAO implements GenericDAO {

    //Toegang tot de Quizmaster database
    public CourseDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    //Alle cursussen in een lijst
    @Override
    public ArrayList getAll() {
        ArrayList<Course> cursusLijst = new ArrayList<>();
        {
            String sql = "SELECT * FROM cursus;";
            try {
                PreparedStatement preparedStatement = getStatement(sql);
                ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
                Course course;
                while (resultSet.next()){
                    String cursusNaam = resultSet.getString("cursusNaam");
                    int userIdcoordinator = resultSet.getInt("userIdcoordinator");
                    course = new Course(cursusNaam, userIdcoordinator);
                    course.setCursusId(resultSet.getInt(1));
                    cursusLijst.add(course);
                }
            } catch (SQLException sqlFout){
                System.out.println(sqlFout.getMessage());
            }
        }return cursusLijst;
    }

    //Een specifieke cursus opvragen op basis van zijn cursusId
    @Override
    public Object getOneById(int cursusId) {
        Course course = null;
            String sql = "SELECT * FROM cursus WHERE cursusID = ?";
            try {
                PreparedStatement preparedStatement = getStatement(sql);
                preparedStatement.setInt(1, cursusId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String cursusNaam = resultSet.getString(2);
                    int userIdcoordinator = resultSet.getInt("userIdcoordinator");
                    course = new Course(cursusNaam, userIdcoordinator);
                    course.setCursusId(resultSet.getInt(1));
                }
            } catch (SQLException sqlFout){
                System.out.println(sqlFout.getMessage());
            } return course;
        }

    //Een specifieke cursus in database wegschrijven
    @Override
    public void storeOne(Object type) {
        Course course = (Course) type; //type casten als course
            String sql = "INSERT INTO cursus (cursusNaam, userIdCoordinator) VALUES (?,?);";
            try{
                new UserDAO(dBaccess);
                PreparedStatement preparedStatement = getStatementWithKey(sql);
                preparedStatement.setString(1, course.getCursusNaam());
                preparedStatement.setInt(2, course.getUserIdCoordinator());
                int key = executeInsertPreparedStatement(preparedStatement);
                course.setCursusId(key);
            } catch (SQLException sqlFout){
                System.out.println(sqlFout.getMessage());
            }
    }

    //Cursus verwijderen
    public void deleteCourse(Course course) {
        String sql = "DELETE FROM cursus WHERE cursusId = ?";
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, course.getCursusId());
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException SQLuitzondering){
            System.out.println(SQLuitzondering.getMessage());
        }
    }

    //Cursusnaam updaten
    public void updateCourseName(Course course){
        String sql = "UPDATE cursus SET cursusNaam = ? WHERE cursusId = ?;";
        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, course.getCursusNaam());
            preparedStatement.setInt(2, course.getCursusId());
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException sqlUitzondering){
            System.out.println(sqlUitzondering.getMessage());
        }
    }

}
