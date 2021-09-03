import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Note {
    private String title;
    private String body;
    private Timestamp timestamp;

    public Note(String title, String body, Timestamp timestamp) {
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
    }

    public void show() {
        System.out.printf(ConsoleColor.TEXT_BLUE_BRIGHT);
        System.out.println("---\t" + title + "\t---\n");
        System.out.printf(ConsoleColor.TEXT_CYAN_BRIGHT);
        System.out.println(body + ConsoleColor.TEXT_RESET);
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return title + "\t\t" + formatter.format(timestamp);
    }
}
