import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private Library library;
    private View currentView;
    private LoginView loginView;
    private UserView userView;
    private AdminView adminView;

    public Controller(Library library, UserView userView, AdminView adminView, LoginView loginView) {
        this.library = library;
        this.userView = userView;
        this.loginView = loginView;
        this.adminView = adminView;
        this.currentView = loginView;
        bindAllButtons();
        initViews();
        currentView.setVisible(true);
    }

    public void initViews() {
        userView.initView();
        adminView.initView();
        loginView.initView();
    }

    public void bindAllButtons(){
        bindUserButtons();
        bindAdminButtons();
        bindLoginButtons();
    }

    public void bindButtons(UserView view) {
        view.getUserButton().addActionListener((e) -> {
            userButtonAction();
        });
        view.getToReadBooksButton().addActionListener((e) -> {
            toReadBooksButtonAction();
        });
        view.getReadBooksButton().addActionListener((e) -> {
            ReadBooksButtonAction();
        });
        view.getLogoutButton().addActionListener((e) -> {
            logoutButtonAction();
        });
        view.getFindBookButton().addActionListener((e) -> {
            findBookButtonAction();
        });
        view.getRatedBooksButton().addActionListener((e) -> {
            ratedBooksButtonAction();
        });
        view.getAddToReadButton().addActionListener((e) -> {
            addToReadButtonAction();
        });
        view.getAddReadButton().addActionListener((e) -> {
            addReadButtonAction();
        });
    }


    public void bindUserButtons(){
        bindButtons(userView);
    }

    public void bindAdminButtons(){
        bindButtons(adminView);
        adminView.getDeleteAccountButton().addActionListener((e) -> {
            deleteAccountButtonAction();
        });
    }
    public void bindLoginButtons(){
        loginView.getLoginButton().addActionListener((e) -> {
            loginButtonAction();
        });
    }
    public void addToReadButtonAction(){
        UserView view = (UserView) currentView;
        if(library.getCurrentlyLoggedUser().getBooksToRead().contains(view.getLastSelectedBook()))
            view.addingDeletingBookMessage("Książka znajduje się już na twojej liście do przeczytania", "");
        else{
            library.getCurrentlyLoggedUser().getBooksToRead().add(view.getLastSelectedBook());
            view.addingDeletingBookMessage("Książka została dodana", "");
        }
    }

    public void addReadButtonAction(){
        UserView view = (UserView) currentView;
        if(library.getCurrentlyLoggedUser().getBooksRead().contains(view.getLastSelectedBook()))
            view.addingDeletingBookMessage("Książka znajduje się już na twojej liście przeczytanych", "");
        else{
            library.getCurrentlyLoggedUser().getBooksRead().add(view.getLastSelectedBook());
            view.addingDeletingBookMessage("Książka została dodana", "");
        }
    }

    public void findBookButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getBooks();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getAddRateButton();
        view.selectBookView(books, button1, button2, button3);

    }

    public void loginButtonAction(){
        currentView.setVisible(false);
        if(library.getCurrentlyLoggedUser().getClass() == Administrator.class){
            adminView.getUserButton().setText("Witaj " + library.getCurrentlyLoggedUser().getName() + "!");
            currentView = adminView;
        }else{
            userView.getUserButton().setText("Witaj " + library.getCurrentlyLoggedUser().getName() + "!");
            currentView = userView;
        }
        currentView.setVisible(true);
    }

    public void logoutButtonAction(){
        currentView.setVisible(false);
        currentView = loginView;
        currentView.setVisible(true);
    }

    public void userButtonAction() {
        UserView view = (UserView) currentView;
        String userName = library.getCurrentlyLoggedUser().getName();
        String labelText = "Hello " + userName + "!";
        view.userButtonView(labelText);

    }

    //taka wersja jest mało MVC, ale i tak to będzie wyglądac inaczej więc na razie nie poprawiam
    /*public void toReadBooksButtonAction(){
        UserView view = (UserView) currentView;
        view.getMainPanel().removeAll();
        ArrayList<Book> booksToRead = library.getCurrentlyLoggedUser().getBooksToRead();
        String output = "";
        for(Book book : booksToRead){
            output = output + book.toString() + "\n";
        }
        view.getMainPanel().add(new JLabel(output));
        view.setVisible(true);
        view.repaint();
    }*/
    public void toReadBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksToRead();
        JButton button1 = view.getDeleteToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getAddRateButton();
        view.selectBookView(books, button1, button2, button3);
    }
    public void ReadBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksRead();
        JButton button2 = view.getDeleteReadButton();
        JButton button1 = view.getAddToReadButton();
        JButton button3 = view.getAddRateButton();
        view.selectBookView(books, button1, button2, button3);
    }

    public void ratedBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksRated();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getDeleteRateButton();
        view.selectBookView(books, button1, button2, button3);
    }


    public void deleteAccountButtonAction(){
        System.out.println("Konto usunięto");
    }

    public static void main (String []arg) throws FileNotFoundException {
        UserView view = new UserView();
        AdminView view1 = new AdminView();
        LoginView view2 = new LoginView();
        ArrayList<Book> books = FileLoader.returnBooksFromFile();
        Library library = new Library(books);
        User user = new User("ania", "haslo123", library);
        Administrator admin = new Administrator("Dorota", "admin1", library);
        //user.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());

        library.setCurrentlyLoggedUser(admin);
        Controller controller = new Controller(library, view, view1, view2);
    }

}