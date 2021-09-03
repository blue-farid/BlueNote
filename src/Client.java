import java.util.ArrayList;

public class Client {

    private final String username;
    private final String password;
    private ArrayList<Note> notes;
    public Client(String username, String password) {
        this.username = username;
        this.password = password;
        this.notes = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
}
