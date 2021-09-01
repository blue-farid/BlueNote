import java.time.LocalDateTime;

public class Note {
    private String title;
    private String body;
    private LocalDateTime date;

    public Note(String title, String body, LocalDateTime date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
