import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Note {
    private final String title;
    private String body;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) && Objects.equals(body, note.body) && Objects.equals(timestamp, note.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, timestamp);
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return title + "\t\t" + formatter.format(timestamp);
    }

    public void setBody(String body) {
        this.body = body;
    }
}
