import java.sql.*;
import java.util.ArrayList;

public class SQLManager {

    private static Connection connection;
    private static Statement statement;

    public static Client getClient(String username) {
        String query = "select * from clients where username='" + username + "'";
        try {
            statement.execute(query);
            statement.getResultSet().next();
            return new Client(statement.getResultSet().getString("username"),
                    statement.getResultSet().getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void addClient(Client client) {
        String query = "insert into clients values (" + "'" +
                client.getUsername() + "'" + ", " + "'" + client.getPassword() + "'" + ")";
        try {
            statement.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static ArrayList<String> getUsernames() {
        String query = "select username from clients";
        try {
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            ArrayList<String> usernames = new ArrayList<>();

            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }
            return usernames;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/blue_note";
        String username = "root";
        String password = "1234";

        try {
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
