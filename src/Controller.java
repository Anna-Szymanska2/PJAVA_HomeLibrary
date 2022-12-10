import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        view.getLogoutButton().addActionListener((e) -> {
            logoutButtonAction();
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
        //ArrayList<Book> booksToRead = library.getCurrentlyLoggedUser().getBooksToRead();
        ArrayList<Book> booksToRead = library.getBooks();
        JList<Book> list = new JList<Book>();
        DefaultListModel <Book> model = new DefaultListModel<>();
        list.setModel(model);
        model.addAll(booksToRead);
        list.setVisibleRowCount(30);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
        JScrollPane scroll = new JScrollPane(list);
        UserView view = (UserView) currentView;
        view.getMainPanel().removeAll();
        //view.getMainPanel().add(list);
        view.getMainPanel().add(scroll);
        view.setVisible(true);
        view.repaint();
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
        User user = new User("ania", "haslo123");
        Administrator admin = new Administrator("Dorota", "admin1");
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