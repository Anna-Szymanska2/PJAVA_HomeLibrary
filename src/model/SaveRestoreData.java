package model;

import java.io.*;

/**
 * SavaRestoreData is a class that enables restoring and saving library data.
 */
public class SaveRestoreData {

    /**
     * Saves library data by creating serialization file.
     * @param library library which data is saved.
     */
    public static void save(Library library){
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("library.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(library);
            out.close(); fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restores library data from serialization file.
     * @return library created thanks to data stored in serialization file.
     */
    public static Library restoreLibrary(){
        Library library;
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream("library.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            library = (Library) in.readObject();
            in.close(); fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return library;
    }

}
