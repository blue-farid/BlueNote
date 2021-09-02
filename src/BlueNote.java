
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class BlueNote implements Runnable {
    private Scanner scanner = new Scanner(System.in);
    private Client client;
    private SQLManager sqlManager = new SQLManager();

    @Override
    public void run() {
        while (true) {
            if (signInAndSignUpMenu())
                break;
        }
        while (true) {
            mainMenu();
        }
    }

    private void mainMenu() {
        System.out.println("1- Add\n2- Remove\n3- Notes\n4- Exit");
        int choose = chooseAnOption(1,4,false);
        if (choose == 1) {
            addNote();
        } else if (choose == 2) {
            removeNote();
        } else if (choose == 3) {
            notesFunc();
        } else if (choose == 4) {
            System.exit(0);
        } else {
            /* It can never fucking happen!*/
        }
    }

    private void removeNote() {
        Note chosenNote = chooseANote();
        client.getNotes().remove(chosenNote);
        SQLManager.removeNote(client, chosenNote);
    }

    private Note chooseANote() {
        if (!displayNotes()) {
            return null;
        }
        int choose = chooseAnOption(1,client.getNotes().size(),true);
        try {
            return client.getNotes().get(choose - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void notesFunc() {
        Note chosenNote = chooseANote();
        if (chosenNote == null)
            return;
        chosenNote.show();
        System.out.println("\nenter 0 to back to main menu");
        while (true) {
            if (scanner.nextLine().equals("0")) {
                break;
            }
        }
    }
    private void addNote() {
        System.out.println("choose a title:");
        String title = scanner.nextLine();
        System.out.println("feel free to write!\nenter '#' to finish!");
        String body = readTheBody();
        Date now = new Date(System.currentTimeMillis());
        Note note = new Note(title,body,now);
        client.getNotes().add(note);
        SQLManager.addNote(client,note);
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
    private boolean signInAndSignUpMenu() {
        System.out.println("1- sign in\n2- sign up");
        int choose = chooseAnOption(1, 2,false);
        if (choose == 1) {
            return singIn();
        } else {
            return singUp();
        }
    }

    private boolean singUp() {
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
        System.out.println("sign up!");
        return true;
    }

    private boolean singIn() {
        System.out.println("enter your username:");
        while (true) {
            String username = scanner.nextLine();
            client = SQLManager.getClient(username);
            if (client == null || client.getUsername() == null) {
                System.out.println("username does not exist!");
                return false;
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
        client.setNotes(SQLManager.findNotes(client));
        return true;
    }

    private boolean displayNotes() {
        ArrayList<Note> notes = client.getNotes();

        if (notes.size() == 0) {
            System.out.println("there is no available note!");
            return false;
        }

        int i = 1;
        for (Note note: notes) {
            System.out.println(i++ + "- " + note);
        }
        return true;
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
    private int chooseAnOption(int indexMin, int indexMax, boolean zeroCondition) {
        int res = -1;
        while (true) {
            try {
                res = Integer.parseInt(scanner.nextLine());
                if (res == 0 && zeroCondition)
                    return res;
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
