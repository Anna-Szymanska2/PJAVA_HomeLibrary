package controller;
import model.Library;
import model.SaveRestoreData;
import view.*;
import java.io.File;

public class Main {
    public static void main (String []arg){
        UserView view = new UserView();
        AdminView view1 = new AdminView();
        LoginView view2 = new LoginView();
        RegisterView view3 = new RegisterView();
        AddBooksView view4 = new AddBooksView();
        Library library = new Library();
        File file = new File("library.ser");
        if (file.exists()) {
            library = SaveRestoreData.restoreLibrary();
        }

        Controller controller = new Controller(library, view, view1, view2, view3, view4);

    }

}
