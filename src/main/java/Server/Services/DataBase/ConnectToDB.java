package Server.Services.DataBase;

import Server.Config.Configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB extends Configs {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String connection = "jdbc:mysql://localhost:3306/CostCalc?user=root&password=1234";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection dbConnection;
        dbConnection = DriverManager.getConnection(connection);
        return dbConnection;
    }
}
