import java.sql.Date;

public class Note {
    private String title;
    private String body;
    private Date date;

    public Note(String title, String body, Date date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public void show() {
        System.out.println("---\t" + title + "\t---");
        System.out.println();
        System.out.println(body);
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return title + "\t\t" + date;
    }
}
