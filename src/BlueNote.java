import java.util.ArrayList;
import java.util.Scanner;

public class BlueNote implements Runnable {
    private Scanner scanner = new Scanner(System.in);
    private Client client;
    private SQLManager sqlManager = new SQLManager();

    @Override
    public void run() {
        mainMenu();
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
        System.out.println("choose a username:");
        String username;
        while (true) {
            username = scanner.nextLine();
            if (checkTheValidityOfTheUsername(username)) {
                break;
            } else {
                System.out.println("the username is invalid! choose another one:");
            }
        }
        System.out.println("choose a password:");
        String password = scanner.nextLine();
        client = new Client(username,password);
        SQLManager.addClient(client);
    }

    private void singIn() {
        System.out.println("enter your username:");
        while (true) {
            String username = scanner.nextLine();
            client = SQLManager.getClient(username);
            if (client == null || client.getUsername() == null) {
                System.out.println("username does not exist!");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("enter your password:");
            String password = scanner.nextLine();
            if (password.equals(client.getPassword())) {
                System.out.println("sign in!");
                break;
            } else {
                System.out.println("password is wrong! try again.");
            }
        }
    }

    private boolean checkTheValidityOfTheUsername(String theUsername) {
        ArrayList<String> usernames = SQLManager.getUsernames();

        for (String username: usernames) {
            if (username.equalsIgnoreCase(theUsername)) {
                return false;
            }
        }
        return true;
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
