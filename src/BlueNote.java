import java.util.InputMismatchException;
import java.util.Scanner;

public class BlueNote implements Runnable {
    private Scanner scanner = new Scanner(System.in);
    private Client client;
    private SQLManager sqlManager = new SQLManager();
    @Override
    public void run() {

    }

    private void mainMenu() {
        System.out.println("1- sign in\n2- sign up");
        int choose = chooseAnOption(1, 2);
        if (choose == 1) {
            singIn();
        } else {
            singUp();
        }
    }

    private void singUp() {
    }

    private void singIn() {
    }

    private int chooseAnOption(int indexMin, int indexMax) {
        int res = -1;
        while (true) {
            try {
                res = Integer.parseInt(scanner.nextLine());
                if (res > indexMax || res < indexMin)
                    throw new IndexOutOfBoundsException();

                break;

            } catch (NumberFormatException e) {
                System.out.println("enter a number!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("out of bounds input!");
            }
        }
        return res;
    }
}
