import java.sql.*;
import java.util.ArrayList;

public class SQLManager {

    private static Connection connection;
    private static Statement statement;

    public static Client getClient(String username) {
        String query = "select * from clients where username = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,username);
            ps.execute();
            ps.getResultSet().next();
            return new Client(ps.getResultSet().getString("username"),
                    ps.getResultSet().getString("password"));
        } catch (SQLException e) {

        }
        return null;
    }

    public static void addNote(Client client, Note note) {
        String query = "insert into notes (username, title, body, noteDate) " +
                "values(?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,client.getUsername());
            ps.setString(2,note.getTitle());
            ps.setString(3, note.getBody());
            ps.setTimestamp(4,note.getTimestamp());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeNote(Client client, Note note) {
        String query = "delete from notes where username = ?" +
                "and title = ? and body = ? and noteDate = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,client.getUsername());
            ps.setString(2,note.getTitle());
            ps.setString(3,note.getBody());
            ps.setTimestamp(4,note.getTimestamp());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addClient(Client client) {
        String query = "insert into clients values (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,client.getUsername());
            ps.setString(2,client.getPassword());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ArrayList<Note> findNotes(Client client) {
        String query = "select * from notes where username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,client.getUsername());
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            ArrayList<Note> notes = new ArrayList<>();
            while (resultSet.next()) {
                notes.add(new Note(resultSet.getString("title"),
                        resultSet.getString("body"), resultSet.getTimestamp("noteDate")));
            }
            return notes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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
    public static void connectToDatabase() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/blue_note";
        String username = "root";
        String password = "1234";
        connection = DriverManager.getConnection(url,username,password);
        statement = connection.createStatement();
    }
}
