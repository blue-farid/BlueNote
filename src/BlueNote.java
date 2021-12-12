import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class BlueNote implements Runnable {
    private final Scanner scanner = new Scanner(System.in);
    private Client client;

    @Override
    public void run() {
        while (true) {
            if (signInAndSignUpMenu())
                break;
        }

        while (true) {
            System.out.println();
            mainMenu();
        }
    }

    private void mainMenu() {
        System.out.println("1- Add\n2- Remove\n3- Notes\n4- Append\n5- Export\n" +
                "6- Exit");
        int choose = chooseAnOption(1,6,false);
        if (choose == 1) {
            addNote();
        } else if (choose == 2) {
            removeNote();
        } else if (choose == 3) {
            notesFunc();
        } else if (choose == 4) {
            appendFunc();
        } else if (choose == 5) {
            exportFunc();
        } else if (choose == 6) {
            System.exit(0);
        } else {
            /* It can never fucking happen!*/
        }
    }

    private void exportFunc() {
        Note chosenNote = chooseANote();

        if (chosenNote == null) {
            return;
        }

        if (new FileUtils().exportNote(chosenNote)) {
            System.out.println("the note has been exported successfully!\n" +
                    "you can find it on 'export' folder.");
        } else {
            System.out.println("something went wrong!\n" +
                    "could not export the file!");
        }
    }

    private void removeNote() {
        Note chosenNote = chooseANote();

        if (chosenNote == null)
            return;

        client.getNotes().remove(chosenNote);
        SQLManager.removeNote(client, chosenNote);
        System.out.println("the note has been removed successfully!");
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
        Main.cls();
        chosenNote.show();
        System.out.println("\nenter 0 to back to main menu");
        while (true) {
            if (scanner.nextLine().equals("0")) {
                break;
            }
        }
    }
    private void addNote() {
        Main.cls();
        System.out.println("choose a title:");
        System.out.print(ConsoleColor.TEXT_BLUE_BRIGHT);
        String title = scanner.nextLine();
        System.out.print(ConsoleColor.TEXT_RESET);
        System.out.println("feel free to write!\nenter '#' to finish!\n");
        String body = readTheBody();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Note note = new Note(title,body,now);
        client.getNotes().add(note);
        SQLManager.addNote(client,note);
        System.out.println("the new note has been saved successfully!");
    }

    private String readTheBody() {
        System.out.print(ConsoleColor.TEXT_CYAN_BRIGHT);
        String body = "";
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("#"))
                break;

            body += input + "\n";
        }
        System.out.print(ConsoleColor.TEXT_RESET);
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
                printError("the username is invalid! choose another one:");
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

        String username = scanner.nextLine();
        client = SQLManager.getClient(username);
        if (client == null || client.getUsername() == null) {
            printError("username does not exist!");
            return false;
        }

        while (true) {
            System.out.println("enter your password:");
            String password = scanner.nextLine();
            if (password.equals(client.getPassword())) {
                System.out.println("sign in!");
                break;
            } else {
                printError("password is wrong! try again.");
            }
        }
        client.setNotes(SQLManager.findNotes(client));
        return true;
    }

    private boolean displayNotes() {
        ArrayList<Note> notes = client.getNotes();

        if (notes.size() == 0) {
            printError("there is no available note!");
            return false;
        }

        int i = 1;
        System.out.print(ConsoleColor.TEXT_BLUE_BRIGHT);
        for (Note note: notes) {
            System.out.println(i++ + "- " + note);
        }
        System.out.print(ConsoleColor.TEXT_RESET);
        return true;
    }

    private void printError(String str) {
        System.out.print(ConsoleColor.TEXT_RED_BRIGHT + str);
        System.out.println(ConsoleColor.TEXT_RESET);
    }

    private boolean checkTheValidityOfTheUsername(String theUsername) {
        ArrayList<String> usernames = SQLManager.getUsernames();

        if (usernames == null)
            return true;

        for (String username: usernames) {
            if (username.equalsIgnoreCase(theUsername)) {
                return false;
            }
        }
        return true;
    }
    private int chooseAnOption(int indexMin, int indexMax, boolean zeroCondition) {
        int res;
        while (true) {
            try {
                res = Integer.parseInt(scanner.nextLine());
                if (res == 0 && zeroCondition)
                    return res;
                if (res > indexMax || res < indexMin)
                    throw new IndexOutOfBoundsException();

                break;

            } catch (NumberFormatException e) {
                printError("enter a number!");
            } catch (IndexOutOfBoundsException e) {
                printError("out of bounds input!");
            }
        }
        return res;
    }

    private void appendFunc() {
        Note chosenNote = chooseANote();
        if (chosenNote == null) {
            return;
        }
        client.getNotes().remove(chosenNote);
        SQLManager.removeNote(client, chosenNote);
        Main.cls();
        chosenNote.show();
        String line = "---------------------------------------------";
        System.out.println(line);
        System.out.println("\nfeel free to write!\nenter '#' to finish!\n");
        String body = readTheBody();
        chosenNote.setBody(chosenNote.getBody() + line + "\n\n" + body);
        client.getNotes().add(chosenNote);
        SQLManager.addNote(client, chosenNote);
    }
}
