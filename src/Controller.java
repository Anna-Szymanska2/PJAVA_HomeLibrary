import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private Library library;
    private UserView currentView;

    public Controller(Library library, UserView view) {
        this.library = library;
        this.currentView = view;
    }

    public void initView() {
        currentView.initView();
        currentView.setVisible(true);
    }

    public void bindButtons() {
        currentView.getUserButton().addActionListener((e) -> {
            userButtonAction();
        });
        currentView.getToReadBooksButton().addActionListener((e) -> {
            toReadBooksButtonAction();
        });
    }

    public void userButtonAction() {
        String userName = library.getCurrentlyLoggedUser().getName();
        String labelText = "Hello " + userName + "!";
        currentView.userButtonView(labelText);

    }

    //taka wersja jest mało MVC, ale i tak to będzie wyglądac inaczej więc na razie nie poprawiam
    public void toReadBooksButtonAction(){
        currentView.getMainPanel().removeAll();
        ArrayList<Book> booksToRead = library.getCurrentlyLoggedUser().getBooksToRead();
        String output = "";
        for(Book book : booksToRead){
            output = output + book.toString() + "\n";
        }
        currentView.getMainPanel().add(new JLabel(output));
        currentView.setVisible(true);
        currentView.repaint();
    }

    public static void main (String []arg) throws FileNotFoundException {
        UserView view = new UserView();
        AdminView view1 = new AdminView();
        ArrayList<Book> books = FileLoader.returnBooksFromFile();
        Library library = new Library(books);
        User user = new User("ania", "haslo123");
        Administrator admin = new Administrator("Dorota", "admin1");
        user.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        library.setCurrentlyLoggedUser(admin);
        Controller controller = new Controller(library, view1);
        controller.initView();
        controller.bindButtons();
    }

}