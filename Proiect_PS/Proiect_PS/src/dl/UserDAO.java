package dl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.User;

public class UserDAO {
    public ArrayList<User> getUsersFromDB() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            Connection connection = DBConnectionSingleton.getDBConnection().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user");
            while (resultSet.next()) {
                User user = new User();
                user.setPassword(resultSet.getString("password_user"));
                user.setUsername(resultSet.getString("name_user"));
                users.add(user);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUserInDB(User user) {

        try {
            Connection connection = DBConnectionSingleton.getDBConnection().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into user (name_user, password_user) values ('" + user.getUsername()
                    + "', '" + user.getPassword() + "');");
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
