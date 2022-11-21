import java.io.*;


public class SaveRestoreData {
    public static void save(Administrator admin){
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("admin.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(admin);
            out.close(); fileOut.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Object has been saved");
    }

    public static Administrator restore(){
        Administrator admin;
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream("C:\\Studia\\V semestr\\PIJAVA\\projekt\\PJAVA_HomeLibrary\\admin.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            admin = (Administrator) in.readObject();
            in.close(); fileIn.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return admin;
    }
    public static void main (String []arg){

    }
}
