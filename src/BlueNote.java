
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BlueNote implements Runnable {
    private Scanner scanner = new Scanner(System.in);
    private Client client;
    private SQLManager sqlManager = new SQLManager();

    @Override
    public void run() {
        signInAndSignUpMenu();
        cls();
        mainMenu();
    }

    private void mainMenu() {
        System.out.println("1- Add\n2- Remove\n3- Notes\n4- Export");
        int choose = chooseAnOption(1,4);
        if (choose == 1) {
            addNote();
        }
    }

    private void addNote() {
        System.out.println("choose a title:");
        String title = scanner.nextLine();
        System.out.println("feel free to write!\nenter '#' to finish!");
        String body = readTheBody();
        LocalDateTime now = LocalDateTime.now();
        client.getNotes().add(new Note(title,body,now));
        System.out.println("the new note has been saved successfully!");
    }

    private String readTheBody() {
        String body = "";
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("#"))
                break;

            body += input + "\n";
        }
        return body;
    }
    private void signInAndSignUpMenu() {
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

    public void cls()
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
