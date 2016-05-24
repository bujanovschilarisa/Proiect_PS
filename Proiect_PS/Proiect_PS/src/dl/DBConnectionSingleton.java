package dl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSingleton {
    private static DBConnectionSingleton dbConnection;
    private Connection connection;

    private DBConnectionSingleton() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/proiect_ps", "root", null);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static DBConnectionSingleton getDBConnection() {
        if (dbConnection == null) {
            dbConnection = new DBConnectionSingleton();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
