import java.io.*;

public class FileUtils {
    public boolean exportNote(Note note) {
        File exportedNote = new File("/export/" + note.getTitle() + ".txt");

        try {
            exportedNote.delete();
            exportedNote.createNewFile();
        } catch (IOException e) {
            return false;
        }

        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(exportedNote));
            out.writeUTF(note.getDisplayFormat());
            return true;
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return false;
    }
}
