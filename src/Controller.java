import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller implements ReminderListener{
    private Library library;
    private View currentView;
    private LoginView loginView;
    private UserView userView;
    private AdminView adminView;
    private RegisterView registerView;

    public Controller(Library library, UserView userView, AdminView adminView, LoginView loginView, RegisterView registerView) {
        this.library = library;
        this.userView = userView;
        this.loginView = loginView;
        this.adminView = adminView;
        this.registerView = registerView;
        this.currentView = loginView;
        bindAllButtons();
        initViews();
        currentView.setVisible(true);
        setRemindersListener();
    }

    public void setRemindersListener(){
        ArrayList<Reminder> reminders = library.getAdmin().getReminders();
        for(Reminder reminder: reminders){
            reminder.setController(this);
        }
    }

    public void initViews() {
        userView.initView();
        userView.addWindowListener(new WindowAdapter() {


            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                SaveRestoreData.save(library);
            }

        });


        adminView.initView();
        adminView.addWindowListener(new WindowAdapter() {


            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                SaveRestoreData.save(library);
            }

        });
        adminView.addWindowListener(new WindowAdapter() {


            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                library.getAdmin().setOrSendReminders();
            }

        });
        loginView.initView();
        loginView.addWindowListener(new WindowAdapter() {


            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                SaveRestoreData.save(library);
            }

        });
        registerView.initView();
        registerView.addWindowListener(new WindowAdapter() {


            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                SaveRestoreData.save(library);
            }

        });
    }

    public void bindAllButtons(){
        bindUserButtons();
        bindAdminButtons();
        bindLoginButtons();
        bindRegisterButtons();
    }

    public void bindButtons(UserView view) {
        view.getUserButton().addActionListener((e) -> userButtonAction());
        view.getToReadBooksButton().addActionListener((e) -> toReadBooksButtonAction());
        view.getReadBooksButton().addActionListener((e) -> ReadBooksButtonAction());
        view.getLogoutButton().addActionListener((e) -> logoutButtonAction());
        view.getRatedBooksButton().addActionListener((e) -> ratedBooksButtonAction());
        view.getAddToReadButton().addActionListener((e) -> addToReadButtonAction());
        view.getAddReadButton().addActionListener((e) -> addReadButtonAction());
        view.getDeleteToReadButton().addActionListener((e) -> deleteToReadButtonAction());
        view.getDeleteReadButton().addActionListener((e) -> deleteReadButtonAction());
        view.getAddRateButton().addActionListener((e) -> addRateButtonAction());
        view.getDeleteRateButton().addActionListener((e) -> deleteRateButtonAction());
    }


    public void bindUserButtons(){
        bindButtons(userView);
        userView.getFindBookButton().addActionListener((e) -> findBookButtonUserAction());
        userView.getBorrowedBooksButton().addActionListener((e) -> borrowedBookButtonUserAction());
        userView.getFilterButton().addActionListener((e) -> filterButtonUserAction());
    }

    public void bindAdminButtons(){
        bindButtons(adminView);
        adminView.getDeleteAccountButton().addActionListener((e) -> deleteAccountButtonAction());
        adminView.getFindBookButton().addActionListener((e) -> findBookButtonAdminAction());
        adminView.getBorrowBookButton().addActionListener((e) -> borrowBookButtonAction());
        adminView.getConfirmBorrowingBookButton().addActionListener((e) -> confirmBorrowingBookButtonAction());
        adminView.getBorrowedBooksButton().addActionListener((e) -> borrowedBookButtonAdminAction());
        adminView.getRemindersButton().addActionListener((e) -> remindersButtonAction());
        adminView.getReturnedBookButton().addActionListener((e) -> returnedBookButtonAction());
        adminView.getPostponeReturningBookButton().addActionListener((e) -> postponeReturningBookButtonAction());
        adminView.getConfirmPostponingReturningBookButton().addActionListener((e) -> confirmPostponingReturningBookButtonAction());
        adminView.getDeleteReminderButton().addActionListener((e) -> deleteReminderButtonAction());
        adminView.getConfirmChoosingAccountButton().addActionListener((e -> confirmChoosingAccountButtonAction()));
        adminView.getFilterButton().addActionListener((e) -> filterButtonAdminAction());
    }
    public void bindLoginButtons(){
        loginView.getLoginButton().addActionListener((e) -> loginButtonAction());
        loginView.getRegisterButton().addActionListener((e) -> registerButtonLoginAction());
    }

    public void bindRegisterButtons(){
        registerView.getRegisterButton().addActionListener((e) -> registerButtonRegisterAction());
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
        view.resetMainPanel();
    }

    public void deleteReadButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().getBooksRead().remove(view.getLastSelectedBook());
        view.addingDeletingBookMessage("Książka została usunięta z twojej listy przeczytanych", "");
        view.resetMainPanel();
    }

    public void addRateButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().addRating(view.getRatingSlider().getValue(),view.getLastSelectedBook());
        view.addingDeletingBookMessage("Ocena ksiązki została dodana", "");
        view.resetMainPanel();
    }

    public void deleteRateButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().removeRating(view.getLastSelectedBook());
        view.addingDeletingBookMessage("Ocena ksiązki została usunięta", "");
        view.resetMainPanel();
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
    public void returnedBookButtonAction() {
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Book returnedBook = view.getLastSelectedBook();
        view.addingDeletingBookMessage("Książka została usunięta z pożyczonych", "");
        admin.bookReturned(returnedBook, library.getUsers());
        view.resetMainPanel();
    }
    public void deleteReminderButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Reminder deletingReminder = view.getLastSelectedReminder();
        view.addingDeletingBookMessage("Przypomnienie zostało usunięte", "");
        admin.deleteReminder(deletingReminder);
        view.resetMainPanel();
    }
    public void postponeReturningBookButtonAction(){
        AdminView view = (AdminView) currentView;
        view.postponeReturningBookView();
    }
 /*   public void postponeReminderButtonAction(){

    }*/

    public void confirmPostponingReturningBookButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Book borrowedBook = view.getLastSelectedBook();
        String time = (String)view.getTimeComboBox().getSelectedItem();
        admin.postponeReturningBook(time, borrowedBook);
        view.resetMainPanel();
        view.addingDeletingBookMessage("Czas na oddanie książki został zwiększony", "");
    }

    public void findBookButtonUserAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getBooks();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton[] buttons = {button1, button2};
        view.findBookView(books, buttons);
    }
    public void findBookButtonAdminAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<Book> books = library.getBooks();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getBorrowBookButton();
        JButton[] buttons = {button1, button2, button3};
        view.findBookView(books, buttons);
    }

    public void filterButtonUserAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.filtration((String) view.getAuthorComboBox().getItemAt(view.getAuthorComboBox().getSelectedIndex()), (Integer) view.getPageCountMinSpinner().getValue(), (Integer) view.getPageCountMaxSpinner().getValue(), (Integer) view.getPublishYearMinBox().getItemAt(view.getPublishYearMinBox().getSelectedIndex()), (Integer) view.getPublishYearMaxBox().getItemAt(view.getPublishYearMaxBox().getSelectedIndex()),(String) view.getGenreComboBox().getItemAt(view.getGenreComboBox().getSelectedIndex()),(Integer) view.getVolumesMinSpinner().getValue(),(Integer) view.getVolumesMaxSpinner().getValue(),(Integer) view.getRatingMinSpinner().getValue(),(Integer) view.getRatingMaxSpinner().getValue());
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton[] buttons = {button1, button2};
        view.findBookView(books, buttons);
    }

    public void filterButtonAdminAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<Book> books = library.filtration((String) view.getAuthorComboBox().getItemAt(view.getAuthorComboBox().getSelectedIndex()), (Integer) view.getPageCountMinSpinner().getValue(), (Integer) view.getPageCountMaxSpinner().getValue(), (Integer) view.getPublishYearMinBox().getItemAt(view.getPublishYearMinBox().getSelectedIndex()), (Integer) view.getPublishYearMaxBox().getItemAt(view.getPublishYearMaxBox().getSelectedIndex()),(String) view.getGenreComboBox().getItemAt(view.getGenreComboBox().getSelectedIndex()),(Integer) view.getVolumesMinSpinner().getValue(),(Integer) view.getVolumesMaxSpinner().getValue(),(Integer) view.getRatingMinSpinner().getValue(),(Integer) view.getRatingMaxSpinner().getValue());
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getBorrowBookButton();
        JButton[] buttons = {button1, button2, button3};
        view.findBookView(books, buttons);
    }
    public void borrowedBookButtonUserAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> borrowedBooks = library.getCurrentlyLoggedUser().getBorrowedBooks();
        JButton[] buttons = {};
        view.selectBookView(borrowedBooks, buttons);
    }

    public void borrowedBookButtonAdminAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<Book> borrowedBooks = library.getCurrentlyLoggedUser().getBorrowedBooks();
        JButton button1 = view.getReturnedBookButton();
        JButton button2 = view.getPostponeReturningBookButton();
        JButton[] buttons = {button1, button2};
        view.selectBookView(borrowedBooks, buttons);
    }

    public void remindersButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        ArrayList<Reminder> reminders = admin.getReminders();
        JButton button1 = view.getDeleteReminderButton();
        //JButton button2 = view.getPostponeReminderButton();
        JButton[] buttons = {button1};
        view.selectReminderView(reminders, buttons);
    }

    public void confirmBorrowingBookButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Book borrowedBook = view.getLastSelectedBook();
        String borrowerName = "";
        String time = (String)view.getTimeComboBox().getSelectedItem();
        Boolean willReminderBeSet = view.getReminderCheckbox().isSelected();
        try{
            User borrower = (User) view.getUsersComboBox().getSelectedItem();
            borrowerName  = borrower.getName();
            borrower.addToBorrowed(borrowedBook);
        }catch (ClassCastException e){
            borrowerName = (String) view.getUsersComboBox().getSelectedItem();
        }
        admin.borrowBook(borrowedBook,borrowerName,time,willReminderBeSet, this);
        view.resetMainPanel();
        if(willReminderBeSet)
            view.addingDeletingBookMessage("Książka została pożyczona i dodano przypomnienie", "");
        else
            view.addingDeletingBookMessage("Książka została pożyczona", "");
    }
    public void borrowBookButtonAction(){
        AdminView view = (AdminView) currentView;
        if(view.getLastSelectedBook().isBorrowed()){
            view.addingDeletingBookMessage("Nie możesz pożyczyć książki, która jest już pożyczona", "");
        }
        else{
            User[] users = getUsersTable();
            view.borrowBookView(users);
        }


    }

    private User[] getUsersTable() {
        ArrayList<User> libraryUsers = library.getUsers();
        Administrator admin = library.getAdmin();
        libraryUsers.remove(admin);
        User[] users = new User[libraryUsers.size()];
        users = libraryUsers.toArray(users);
        return users;
    }


    public void loginButtonAction(){
        User loggedUser = null;
        String name = loginView.getUsernameField().getText();
        char[] password = loginView.getPasswordField().getPassword();

        if (library.namesAndPasswords.containsKey(name) && Arrays.equals(library.namesAndPasswords.get(name), password)) {
                int i = 0;
                for (User u : library.users) {
                    if (u.getName().equals(name)) {
                        loggedUser = library.users.get(i);
                        library.setCurrentlyLoggedUser(loggedUser);
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
                    i++;
                }
            } else
                JOptionPane.showMessageDialog(currentView, "Podano błędną nazwę użytkownika lub hasło", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void registerButtonLoginAction(){
        currentView.setVisible(false);
        currentView = registerView;
        currentView.setVisible(true);
    }
    public void registerButtonRegisterAction(){
        String name = registerView.getUsernameField().getText();
        char[] password = registerView.getPasswordField().getPassword();
        char[] confirmPassword = registerView.getConfirmPasswordField().getPassword();

        if (library.namesAndPasswords.containsKey(name)){
            JOptionPane.showMessageDialog(currentView, "Użytkownik o tej nazwie już istnieje", "Error", JOptionPane.ERROR_MESSAGE);
        }else if (!Arrays.equals(password, confirmPassword)){
            JOptionPane.showMessageDialog(currentView, "Pola 'Hasło' oraz 'Powtórz hasło' różnią się od siebie", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            User user = new User(name,password,library);
            currentView = loginView;
            currentView.setVisible(true);
        }
    }

    public void logoutButtonAction(){
        UserView view = (UserView) currentView;
        currentView.setVisible(false);
        view.getMainPanel().removeAll();
        view.repaint();
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
        ArrayList<Book> booksRated = library.getCurrentlyLoggedUser().getBooksRated();
        String name = library.getCurrentlyLoggedUser().getName();
        JButton button1 = view.getDeleteReadButton();
        JButton button2 = view.getAddRateButton();
        JButton[] buttons = {button1, button2};
        view.selectReadBookView(books,booksRated,name, buttons);
    }

    public void ratedBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksRated();
        String name = library.getCurrentlyLoggedUser().getName();
        JButton button1= view.getDeleteRateButton();
        JButton[] buttons = {button1};
        view.selectRatedBookView(books, name,buttons);
    }


    public void deleteAccountButtonAction(){
        AdminView view = (AdminView) currentView;
        view.deleteAccountView(getUsersTable());
    }
    public void confirmChoosingAccountButtonAction(){
        AdminView view = (AdminView) currentView;
        User user = (User)view.getUsersComboBox().getSelectedItem();
        if(view.showConfirmingDeletingAccountDialog() == 0){
            library.getAdmin().deleteUser(user, library.getUsers());
            view.addingDeletingBookMessage("Konto zostało usunięte","");
        }
        else
            view.addingDeletingBookMessage("Konto nie zostało usunięte","");
        view.resetMainPanel();

    }

    public static void main (String []arg) throws FileNotFoundException {
        UserView view = new UserView();
        AdminView view1 = new AdminView();
        LoginView view2 = new LoginView();
        RegisterView view3 = new RegisterView();
        ArrayList<Book> books = FileLoader.returnBooksFromFile();
        //Library library = new Library(books);
        Library library = SaveRestoreData.restoreLibrary();
        //library.books = books;

        /*User user = new User("ania", "haslo123", library);
        User user2 = new User("Domcia", "345", library);
        Administrator admin = new Administrator("Dorota", "admin1", library);*/
        //library.setCurrentlyLoggedUser(library.getAdmin());
        Controller controller = new Controller(library, view, view1, view2, view3);

    }


    @Override
    public void reminderSendAction(Reminder reminder) {
        AdminView view = (AdminView) currentView;
        view.setLastSelectedBook(reminder.getBorrowedBook());
        view.setLastSelectedReminder(reminder);
        int chosenAction = view.reminderWasSendView(reminder.returnReminderMessage());
        switch (chosenAction){
            case 0-> postponeReturningBookButtonAction();
            case 1-> returnedBookButtonAction();
            case 2-> deleteReminderButtonAction();
        }
    }
}