package database.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleDAO extends AbstractDAO{

    public RoleDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    public ArrayList<String> getAllRoles(){
        ArrayList<String> stringArrayList = new ArrayList<>();

        String roleString = "";

        String sql = "SELECT * FROM rol;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);

            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                roleString = resultSet.getString(2);
                stringArrayList.add(roleString);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return stringArrayList;
    }
}
