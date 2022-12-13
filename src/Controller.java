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
        view.getRatedBooksButton().addActionListener((e) -> {
            ratedBooksButtonAction();
        });
        view.getAddToReadButton().addActionListener((e) -> {
            addToReadButtonAction();
        });
        view.getAddReadButton().addActionListener((e) -> {
            addReadButtonAction();
        });
        view.getDeleteToReadButton().addActionListener((e) -> {
            deleteToReadButtonAction();
        });
        view.getDeleteReadButton().addActionListener((e) -> {
            deleteReadButtonAction();
        });

    }


    public void bindUserButtons(){
        bindButtons(userView);
        userView.getFindBookButton().addActionListener((e) -> {
            findBookButtonUserAction();
        });
    }

    public void bindAdminButtons(){
        bindButtons(adminView);
        adminView.getDeleteAccountButton().addActionListener((e) -> {
            deleteAccountButtonAction();
        });
        adminView.getFindBookButton().addActionListener((e) -> {
            findBookButtonAdminAction();
        });
        adminView.getBorrowBookButton().addActionListener((e) -> {
            borrowBookButtonAction();
        });
        adminView.getConfirmBorrowingBookButton().addActionListener((e) -> {
            confirmBorrowingBookButtonAction();
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

    public void deleteToReadButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().getBooksToRead().remove(view.getLastSelectedBook());
        view.addingDeletingBookMessage("Książka została usunięta z twojej listy do przeczytania", "");
    }

    public void deleteReadButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().getBooksRead().remove(view.getLastSelectedBook());
        view.addingDeletingBookMessage("Książka została usunięta z twojej listy przeczytanych", "");
    }

    public void addReadButtonAction(){
        UserView view = (UserView) currentView;
        if(library.getCurrentlyLoggedUser().getBooksRead().contains(view.getLastSelectedBook()))
            view.addingDeletingBookMessage("Książka znajduje się już na twojej liście przeczytanych", "");
        else{
            library.getCurrentlyLoggedUser().getBooksRead().add(view.getLastSelectedBook());
            if(library.getCurrentlyLoggedUser().getBooksToRead().contains(view.getLastSelectedBook())){
                view.addingDeletingBookMessage("Książka została dodana i usunięta z listy do przeczytania", "");
                library.getCurrentlyLoggedUser().getBooksToRead().remove(view.getLastSelectedBook());
            }else{
                view.addingDeletingBookMessage("Książka została dodana", "");
            }
        }
    }

    public void findBookButtonUserAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getBooks();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton[] buttons = {button1, button2};
        view.selectBookView(books, buttons);
    }
    public void findBookButtonAdminAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<Book> books = library.getBooks();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getBorrowBookButton();
        JButton[] buttons = {button1, button2, button3};
        view.selectBookView(books, buttons);
    }

    public void confirmBorrowingBookButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Book borrowedBook = view.getLastSelectedBook();
        String borrowerName = "";
        try{
            User borrower = (User) view.getUsersComboBox().getSelectedItem();
            borrowerName  = borrower.getName();
            borrower.addToBorrowed(borrowedBook);
        }catch (ClassCastException e){
            borrowerName = (String) view.getUsersComboBox().getSelectedItem();
        }
        System.out.println(borrowerName);
        borrowedBook.setBorrowerName(borrowerName);
        admin.addToBorrowed(borrowedBook);
    }
    public void borrowBookButtonAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<User> libraryUsers = library.getUsers();
        Administrator admin = library.getAdmin();
        libraryUsers.remove(admin);
        User[] users = new User[libraryUsers.size()];
        users = libraryUsers.toArray(users);
        view.borrowBookView(users);

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
        UserView view = (UserView) currentView;
        currentView.setVisible(false);
        view.getMainPanel().removeAll();
        view.repaint();
        currentView = loginView;
        currentView.setVisible(true);
    }

    public void userButtonAction() {
        UserView view = (UserView) currentView;
        String userName = library.getCurrentlyLoggedUser().getName();
        String labelText = "Hello " + userName + "!";
        view.userButtonView(labelText);

    }

    public void toReadBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksToRead();
        JButton button1 = view.getDeleteToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton[] buttons = {button1, button2};
        view.selectBookView(books, buttons);
    }
    public void ReadBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksRead();
        JButton button1 = view.getDeleteReadButton();
        JButton button2 = view.getAddRateButton();
        JButton[] buttons = {button1, button2};
        view.selectBookView(books, buttons);
    }

    public void ratedBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksRated();
        JButton button1= view.getDeleteRateButton();
        JButton[] buttons = {button1};
        view.selectBookView(books, buttons);
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
        User user2 = new User("Domcia", "345", library);
        Administrator admin = new Administrator("Dorota", "admin1", library);
        //user.chooseAndAddToBeRead(library.getBooks());
        /*admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());
        admin.chooseAndAddToBeRead(library.getBooks());*/

        library.setCurrentlyLoggedUser(admin);
        Controller controller = new Controller(library, view, view1, view2);
    }

}