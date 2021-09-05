import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Note {
    private final String title;
    private final String body;
    private final Timestamp timestamp;

    public Note(String title, String body, Timestamp timestamp) {
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
    }

    public void show() {
        System.out.print(ConsoleColor.TEXT_BLUE_BRIGHT);
        System.out.println("---\t" + title + "\t---\n");
        System.out.print(ConsoleColor.TEXT_CYAN_BRIGHT);
        System.out.println(body + ConsoleColor.TEXT_RESET);
    }

    public String getDisplayFormat() {
        return "---\t" + title + "\t---\n\n" + body;
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
