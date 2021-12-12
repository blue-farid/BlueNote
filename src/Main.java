import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("connecting to database...");
            SQLManager.connectToDatabase();
        } catch (SQLException e) {
            Main.cls();
            System.out.println("can't connect to database...");
            new Scanner(System.in).nextLine();
        }

        try {
            int display = new ProcessBuilder("cmd", "/c", "color", "00").inheritIO().start().waitFor();
            System.out.print(display);
            cls();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        new BlueNote().run();
    }

    public static void cls()
    {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }
}
