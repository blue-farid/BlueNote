import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SQLManager.connectToDatabase();

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
