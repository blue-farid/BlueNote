import java.io.*;
import java.net.URISyntaxException;

public class FileUtils {
    public boolean exportNote(Note note) {
        File exportedNote;
        new File("export").mkdir();
        try {
            String jarPath = getClass().getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath();

            String jarName = jarPath.substring(jarPath.lastIndexOf("/") + 1);

            exportedNote = new File(jarPath.replace(jarName,"") + "export/" + note.getTitle() +
                    ".txt");

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        try {
            if (exportedNote.exists()) {
                exportedNote.delete();
            }
            exportedNote.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            FileWriter out = new FileWriter(exportedNote);
            out.write(note.getDisplayFormat());
            out.flush();
            out.close();
            return true;
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return false;
    }
}
