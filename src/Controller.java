import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private Library library;
    private UserView view;

    public Controller(Library library, UserView view) {
        this.library = library;
        this.view = view;
    }

    public void initView() {
        view.initView();
    }

    public void bindButtons() {
        view.getUserButton().addActionListener((e) -> {
            userButtonAction();
        });
        view.getToReadBooksButton().addActionListener((e) -> {
            toReadBooksButtonAction();
        });
    }

    public void userButtonAction() {
        view.getMainPanel().removeAll();
        String userName = library.getCurrentlyLoggedUser().getName();
        view.getMainPanel().add(new JLabel("Hello " + userName + "!"));
        view.setVisible(true);
        view.repaint();

    }

    public void toReadBooksButtonAction(){
        view.getMainPanel().removeAll();
        ArrayList<Book> booksToRead = library.getCurrentlyLoggedUser().getBooksToRead();
        String output = "";
        for(Book book : booksToRead){
            output = output + book.toString() + "\n";
        }
        view.getMainPanel().add(new JLabel(output));
        view.setVisible(true);
        view.repaint();
    }

    public static void main (String []arg) throws FileNotFoundException {
        UserView view = new UserView();
        ArrayList<Book> books = FileLoader.returnBooksFromFile();
        Library library = new Library(books);
        User user = new User("ania", "haslo123");
        user.chooseAndAddToBeRead(library.getBooks());
        library.setCurrentlyLoggedUser(user);
        Controller controller = new Controller(library, view);
        controller.initView();
        controller.bindButtons();
    }

}