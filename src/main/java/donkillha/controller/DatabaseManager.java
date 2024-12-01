package donkillha.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseManager {

    private Connection mysqlConnection;

    public DatabaseManager() {
        Dotenv dotenv = Dotenv.load();
        String user = dotenv.get("MYSQL_USER");
        String password = dotenv.get("MYSQL_PASSWORD");
        try {
            this.mysqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/", user, password);
            // In case there wasn't a database ready yet
            this.executeQuery("CREATE DATABASE IF NOT EXISTS faculty",null);
            //We must use the faculty db since our connection is 
            this.executeQuery("USE faculty",null);
            System.out.println("Connection Established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Object executeQuery(String query, List<Object> variables) {
        try {
            PreparedStatement statement = mysqlConnection.prepareStatement(query);
            if (variables != null){
                for (int i = 0; i < variables.size(); i++) {
                    Object element=variables.get(i);
                    if(element instanceof String){
                        statement.setString(i+1,(String)element);
                    }
                    else if(element instanceof Integer){
                        statement.setInt(i+1,(int) element);
                    }
                    else if(element instanceof Double){
                        statement.setDouble(i+1, (double) element);
                    }
                    else{
                        statement.setObject(i+1, element);
                    }
                }
            }

            boolean operationChosen = statement.execute();
            if (operationChosen){
                // It's a SELECT 
                ResultSet result = statement.getResultSet();
                return result;
            }else{
                // It's UPDATE/INSERT/DELETE operation
                int result= statement.getUpdateCount();
                return result;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;

    }

    

}
