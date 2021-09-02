
public class Main {
    public static void main(String[] args) {
        SQLManager.connectToDatabase();
        new BlueNote().run();
    }
}
