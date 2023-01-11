package controller;
import model.*;
import view.AddBooksView;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.*;
import view.*;

/**
 * Controller is a class used to control changes between the views and actions of the buttons.
 */
public class Controller implements ReminderListener{
    private Library library;
    private View currentView;
    private LoginView loginView;
    private UserView userView;
    private AdminView adminView;
    private RegisterView registerView;
    /**
     * the view which is shown just once after admin registers.
     */
    private AddBooksView addBooksView;
    /**
     * The action that was lastly chosen when reminders dialog appeared.
     */
    private int lastChosenAction = 1;

    /**
     * Constructor that except of setting all views and the library initializes all views, binds all buttons in views to
     * their actions, sets the current view as a loginView and sets itself as a reminder listener to all reminders that
     * admin of the library has.
     * @param library
     * @param userView
     * @param adminView
     * @param loginView
     * @param registerView
     * @param addBooksView
     */
    public Controller(Library library, UserView userView, AdminView adminView, LoginView loginView, RegisterView registerView, AddBooksView addBooksView) {
        this.library = library;
        this.userView = userView;
        this.loginView = loginView;
        this.adminView = adminView;
        this.registerView = registerView;
        this.addBooksView = addBooksView;
        this.currentView = loginView;
        bindAllButtons();
        initViews();
        currentView.setVisible(true);
        setRemindersListener();
    }

    /**
     * Sets itself as a reminder listener to all reminders that admin of the library has.
     */
    public void setRemindersListener(){
        if(library.getAdmin() == null)
            return;
        ArrayList<Reminder> reminders = library.getAdmin().getReminders();
        for(Reminder reminder: reminders){
            reminder.setController(this);
        }
    }

    /**
     * Initializes all views, adds saving library when views are being closed (except addBooksView).
     */
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
        addBooksView.initView();
    }

    /**
     * Adds ActionListeners to all buttons from all views.
     */
    public void bindAllButtons(){
        bindUserButtons();
        bindAdminButtons();
        bindLoginButtons();
        bindRegisterButtons();
        bindAddBooksButtons();
    }

    /**
     * Adds ActionListeners to all buttons from adminView and userView that they have in common.
     * @param view view whose buttons are bound.
     */
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
        view.getRecommendBookButton().addActionListener((e) -> recommendButtonUserAction());
    }

    /**
     * Adds ActionListeners to all buttons from userView that are specific just for it.
     */
    public void bindUserButtons(){
        bindButtons(userView);
        userView.getFindBookButton().addActionListener((e) -> findBookButtonUserAction());
        userView.getBorrowedBooksButton().addActionListener((e) -> borrowedBookButtonUserAction());
        userView.getFilterButton().addActionListener((e) -> filterButtonUserAction());
    }
    /**
     * Adds ActionListeners to all buttons from adminView that are specific just for it.
     */
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
        adminView.getAddBookButton().addActionListener((e) -> addBookButtonAction());
        adminView.getConfirmAddingBookButton().addActionListener((e) -> confirmAddingBookButtonAction());
    }

    /**
     * Adds ActionListeners for buttons in login view.
     */
    public void bindLoginButtons(){
        loginView.getLoginButton().addActionListener((e) -> loginButtonAction());
        loginView.getRegisterButton().addActionListener((e) -> registerButtonLoginAction());
    }

    /**
     * Adds ActionListeners for buttons in register view.
     */
    public void bindRegisterButtons(){
        registerView.getRegisterButton().addActionListener((e) -> registerButtonRegisterAction());
    }
    /**
     * Adds ActionListeners for buttons in add books view.
     */
    public void bindAddBooksButtons(){
        addBooksView.getSelectBooksButton().addActionListener((e) -> selectBooksButtonAction());
    }

    /**
     * Shows view in which is possible to add new book.
     */
    public void addBookButtonAction(){
        AdminView view = (AdminView) currentView;
        view.addBookView();

    }

    /**
     * Checks whether values given by user in adding book view are correct and if they are, adds new book to library.
     */
    public void confirmAddingBookButtonAction(){
        AdminView view = (AdminView) currentView;
        int i = 0;
        for(JTextField textField: view.getAddBookTextFields()){
                if(textField.getText().equals("") && i < 5){
                    view.showErrorMessage("Jedno z obowiązkowych pól nie jest uzupełnione");
                    return;
                }
                i++;
        }
        String series = view.getAddBookTextFields()[5].getText();
        String volumeString = view.getAddBookTextFields()[6].getText();
        if((series.equals("") && !volumeString.equals("")) || (volumeString.equals("") && !series.equals(""))) {
            view.showErrorMessage("Nie podałeś wszytkich danych odnośnie serii");
            return;
        }
        String title = view.getAddBookTextFields()[0].getText();
        String author = view.getAddBookTextFields()[1].getText();
        String genre = view.getAddBookTextFields()[4].getText();

        try{
            int pages = Integer.parseInt(view.getAddBookTextFields()[2].getText());
            int publishYear = Integer.parseInt(view.getAddBookTextFields()[3].getText());
            int volume = 0;
            if(!volumeString.equals("")){
                volume = Integer.parseInt(volumeString);
                if(volume < 0)
                    throw new NumberFormatException();
            }else {
                series = null;
            }
            Calendar currentDate = Calendar.getInstance();
            if(pages < 0 || publishYear < 0)
                throw new NumberFormatException();
            if(publishYear > (int)currentDate.get(Calendar.YEAR))
                throw new NumberFormatException();
            library.getAdmin().addBook(library, title, author, pages, publishYear, genre, series, volume);
            view.showPlainMessage("Książka została dodana do biblioteki", "");
            view.resetMainPanel();
        }catch(NumberFormatException e){
            view.showErrorMessage("Podałeś dane w złym formacie");
        }catch (SimilarBookException e){
            view.showErrorMessage(e.getMessage());
        }

    }

    /**
     * Gives possibility to choose file from which books should be returned. Checks whether file is appropriate and if
     * it is adds all book from file to library.
     */

    public void selectBooksButtonAction(){
        AddBooksView view = (AddBooksView) currentView;
        int response = view.getFileChooser().showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            try {
                ArrayList<Book> books =  FileLoader.returnBooksFromFile(view.getFileChooser().getSelectedFile().getAbsolutePath());
                library.setBooks(books);
                view.showPlainMessage("Książki zostały dodane do biblioteki", "");
                currentView.setVisible(false);
                currentView = loginView;
                currentView.setVisible(true);
            } catch (IOException e) {
                view.showErrorMessage(e.getMessage());
            }catch (NoSuchElementException|NumberFormatException e){
                view.showErrorMessage("Plik, który wybrałeś ma zły format");
            }

        }

    }

    /**
     * Adds selected by user book to list of book he wants to read if book hasn't been there already. Displays
     * communicate about it.
     */
    public void addToReadButtonAction(){
        UserView view = (UserView) currentView;
        if(library.getCurrentlyLoggedUser().getBooksToRead().contains(view.getLastSelectedBook()))
            view.showPlainMessage("Książka znajduje się już na twojej liście do przeczytania", "");
        else{
            library.getCurrentlyLoggedUser().getBooksToRead().add(view.getLastSelectedBook());
            view.showPlainMessage("Książka została dodana", "");
        }
    }

    /**
     * Deletes selected by user book from list of book he wants to read. Displays communicate about it.
     */
    public void deleteToReadButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().getBooksToRead().remove(view.getLastSelectedBook());
        view.showPlainMessage("Książka została usunięta z twojej listy do przeczytania", "");
        view.resetMainPanel();
    }
    /**
     * Deletes selected by user book from list of book he read. Displays communicate about it.
     */
    public void deleteReadButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().getBooksRead().remove(view.getLastSelectedBook());
        view.showPlainMessage("Książka została usunięta z twojej listy przeczytanych", "");
        view.resetMainPanel();
    }

    /**
     * Adds rating given by user to all ratings for the chosen book and resets value of rating slider.
     */
    public void addRateButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().addRating(view.getRatingSlider().getValue(),view.getLastSelectedBook());
        view.showPlainMessage("Ocena książki została dodana", "");
        view.getRatingSlider().setValue(5);
        view.resetMainPanel();

    }

    /**
     * Removes rating given by user form all ratings for the chosen book.
     */
    public void deleteRateButtonAction(){
        UserView view = (UserView) currentView;
        library.getCurrentlyLoggedUser().removeRating(view.getLastSelectedBook());
        view.showPlainMessage("Ocena ksiązki została usunięta", "");
        view.resetMainPanel();
    }

    /**
     * Adds selected by user book to list of book he read if book hasn't been there already. It also deletes this book
     * from list of book user wanted to read if this book was there. Displays communicate about it.
     */
    public void addReadButtonAction(){
        UserView view = (UserView) currentView;
        if(library.getCurrentlyLoggedUser().getBooksRead().contains(view.getLastSelectedBook()))
            view.showPlainMessage("Książka znajduje się już na twojej liście przeczytanych", "");
        else{
            library.getCurrentlyLoggedUser().getBooksRead().add(view.getLastSelectedBook());
            if(library.getCurrentlyLoggedUser().getBooksToRead().contains(view.getLastSelectedBook())){
                view.showPlainMessage("Książka została dodana i usunięta z listy do przeczytania", "");
                library.getCurrentlyLoggedUser().getBooksToRead().remove(view.getLastSelectedBook());
            }else{
                view.showPlainMessage("Książka została dodana", "");
            }
        }
    }

    /**
     * Deletes book from the list of borrowed books.
     */
    public void returnedBookButtonAction() {
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Book returnedBook = view.getLastSelectedBook();
        view.showPlainMessage("Książka została usunięta z pożyczonych", "");
        admin.bookReturned(returnedBook, library.getUsers());
        view.resetMainPanel();
    }

    /**
     * Deletes reminder from list of reminders and cancels its timer.
     */
    public void deleteReminderButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Reminder deletingReminder = view.getLastSelectedReminder();
        view.showPlainMessage("Przypomnienie zostało usunięte", "");
        admin.getRemindersToDelete().add(deletingReminder);
        deletingReminder.cancelReminderTimer();
        view.resetMainPanel();
    }

    /**
     * Shows view that enables to postpone the date of returning book.
     */
    public void postponeReturningBookButtonAction(){
        AdminView view = (AdminView) currentView;
        view.postponeReturningBookView();
    }

    /**
     * Postpones date of returning selected by user book, if reminder was set its time is also postponed.
     */
    public void confirmPostponingReturningBookButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Book borrowedBook = view.getLastSelectedBook();
        String time = (String)view.getTimeComboBox().getSelectedItem();
        admin.postponeReturningBook(time, borrowedBook);
        view.resetMainPanel();
        view.showPlainMessage("Czas na oddanie książki został zwiększony", "");
        lastChosenAction = 1;
        library.getAdmin().deleteReminders();
        library.getAdmin().setOrSendReminders();
        ;
    }

    /**
     * Changes view to view where it's possible to find book with user buttons.
     */
    public void findBookButtonUserAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getBooks();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton[] buttons = {button1, button2};
        view.findBookView(books, buttons);
    }
    /**
     * Changes view to view where it's possible to find book with admin buttons.
     */
    public void findBookButtonAdminAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<Book> books = library.getBooks();
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getBorrowBookButton();
        JButton[] buttons = {button1, button2, button3};
        view.findBookView(books, buttons);
    }

    /**
     * Generates list of books recommended for user and chooses 10 of them randomly to display with
     * array of buttons.
     * If user has read less than 10 books this action won't be performed and a dialog window will be displayed.
     *
     */
    public void recommendButtonUserAction(){
        UserView view = (UserView) currentView;
        if(library.getCurrentlyLoggedUser().getBooksRead().size()>=10) {
            ArrayList<Book> books = new ArrayList<>();
            Random randomGenerator = new Random();
            List<Book> booksRecommended = library.getCurrentlyLoggedUser().recommendBooks(library);
            while(books.size()<10){
                int index = randomGenerator.nextInt(booksRecommended.size());
                if(!books.contains(booksRecommended.get(index)))
                    books.add(booksRecommended.get(index));
            }
            JButton button1 = view.getAddToReadButton();
            JButton button2 = view.getAddReadButton();
            JButton[] buttons = {button1, button2};
            view.selectBookView(books, buttons);
        }
        else
            view.showPlainMessage("<html>Musisz mieć przynajmniej 10 książek dodanych do listy przeczytanych,<br/> aby aplikacja mogła ci coś polecić</html>", "");
    }

    /**
     * Performs filtering function on the list of books in the library using parameters chosen by user.
     * Displays filtered books with an array of buttons.
     */
    public void filterButtonUserAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.filtration((String) view.getAuthorComboBox().getItemAt(view.getAuthorComboBox().getSelectedIndex()), (Integer) view.getPageCountMinSpinner().getValue(), (Integer) view.getPageCountMaxSpinner().getValue(), (Integer) view.getPublishYearMinBox().getItemAt(view.getPublishYearMinBox().getSelectedIndex()), (Integer) view.getPublishYearMaxBox().getItemAt(view.getPublishYearMaxBox().getSelectedIndex()),(String) view.getGenreComboBox().getItemAt(view.getGenreComboBox().getSelectedIndex()),(Integer) view.getVolumesMinSpinner().getValue(),(Integer) view.getVolumesMaxSpinner().getValue(),(Integer) view.getRatingMinSpinner().getValue(),(Integer) view.getRatingMaxSpinner().getValue());
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton[] buttons = {button1, button2};
        view.findBookView(books, buttons);
    }

    /**
     * Performs filtering function on the list of books in the library using parameters chosen by administrator.
     * Displays filtered books with an array of buttons which is different from an array of user buttons.
     */
    public void filterButtonAdminAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<Book> books = library.filtration((String) view.getAuthorComboBox().getItemAt(view.getAuthorComboBox().getSelectedIndex()), (Integer) view.getPageCountMinSpinner().getValue(), (Integer) view.getPageCountMaxSpinner().getValue(), (Integer) view.getPublishYearMinBox().getItemAt(view.getPublishYearMinBox().getSelectedIndex()), (Integer) view.getPublishYearMaxBox().getItemAt(view.getPublishYearMaxBox().getSelectedIndex()),(String) view.getGenreComboBox().getItemAt(view.getGenreComboBox().getSelectedIndex()),(Integer) view.getVolumesMinSpinner().getValue(),(Integer) view.getVolumesMaxSpinner().getValue(),(Integer) view.getRatingMinSpinner().getValue(),(Integer) view.getRatingMaxSpinner().getValue());
        JButton button1 = view.getAddToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton button3 = view.getBorrowBookButton();
        JButton[] buttons = {button1, button2, button3};
        view.findBookView(books, buttons);
    }

    /**
     * Changes the view to a view which displays list of borrowed books.
     */
    public void borrowedBookButtonUserAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> borrowedBooks = library.getCurrentlyLoggedUser().getBorrowedBooks();
        JButton[] buttons = {};
        view.selectBookView(borrowedBooks, buttons);
    }
    /**
     * Changes the view to a view which displays list of borrowed books with admin buttons.
     */
    public void borrowedBookButtonAdminAction(){
        AdminView view = (AdminView) currentView;
        ArrayList<Book> borrowedBooks = library.getCurrentlyLoggedUser().getBorrowedBooks();
        JButton button1 = view.getReturnedBookButton();
        JButton button2 = view.getPostponeReturningBookButton();
        JButton[] buttons = {button1, button2};
        view.selectBookView(borrowedBooks, buttons);
    }
    /**
     * Changes the view to a view which displays list of reminders.
     */
    public void remindersButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        admin.deleteReminders();
        ArrayList<Reminder> reminders = admin.getReminders();
        JButton button1 = view.getDeleteReminderButton();
        //JButton button2 = view.getPostponeReminderButton();
        JButton[] buttons = {button1};
        view.selectReminderView(reminders, buttons);
    }

    /**
     * Borrows the last selected by admin book to the user or to the person that doesn't have an account.
     * User/person and time for which the book borrowed is taken from combobox. Also, it sets reminder if checkbox
     * was selected.
     */
    public void confirmBorrowingBookButtonAction(){
        AdminView view = (AdminView) currentView;
        Administrator admin = library.getAdmin();
        Book borrowedBook = view.getLastSelectedBook();
        String borrowerName = "";
        String time = (String)view.getTimeComboBox().getSelectedItem();
        Boolean willReminderBeSet = view.getReminderCheckbox().isSelected();
        try{
            User borrower = (User) view.getUsersComboBox().getSelectedItem();
            if(borrower == null){
                view.showErrorMessage("Musisz podać imię pożyczającej osoby");
                return;
            }

            borrowerName  = borrower.getName();
            borrower.addToBorrowed(borrowedBook);
        }catch (ClassCastException e){
            borrowerName = (String) view.getUsersComboBox().getSelectedItem();
        }
        admin.borrowBook(borrowedBook,borrowerName,time,willReminderBeSet, this);
        view.resetMainPanel();
        if(willReminderBeSet)
            view.showPlainMessage("Książka została pożyczona i dodano przypomnienie", "");
        else
            view.showPlainMessage("Książka została pożyczona", "");
    }

    /**
     * Checks if the last selected book can be borrowed and if it does, it changes the view in which you can choose
     * borrowing book details.
     */
    public void borrowBookButtonAction(){
        AdminView view = (AdminView) currentView;
        if(view.getLastSelectedBook().isBorrowed()){
            view.showPlainMessage("Nie możesz pożyczyć książki, która jest już pożyczona", "");
        }
        else{
            User[] users = getUsersTable();
            view.borrowBookView(users);
        }


    }

    /**
     * Creates an array of users of the library without the admin.
     * @return an array of users of the library
     */
    private User[] getUsersTable() {
        ArrayList<User> libraryUsers = new ArrayList<>(library.getUsers());
        Administrator admin = library.getAdmin();
        libraryUsers.remove(admin);
        User[] users = new User[libraryUsers.size()];
        users = libraryUsers.toArray(users);
        return users;
    }

    /**
     * Method checks if name and password given by user is correct.
     * If it is, method logs user into their account. If either name of password is not correct matching
     * dialog window is displayed.
     */
    public void loginButtonAction(){
        User loggedUser = null;
        String name = loginView.getUsernameField().getText();
        char[] password = loginView.getPasswordField().getPassword();

        if (library.getNamesAndPasswords().containsKey(name) && Arrays.equals(library.getNamesAndPasswords().get(name), password)) {
                int i = 0;
                for (User u : library.getUsers()) {
                    if (u.getName().equals(name)) {
                        loggedUser = library.getUsers().get(i);
                        library.setCurrentlyLoggedUser(loggedUser);
                        currentView.setVisible(false);
                        if(library.getCurrentlyLoggedUser().getClass() == Administrator.class){
                            adminView.getUserButton().setText("Witaj " + library.getCurrentlyLoggedUser().getName() + "!");
                            currentView = adminView;
                            currentView.setVisible(true);
                            library.getAdmin().deleteReminders();
                            library.getAdmin().setOrSendReminders();
                        }else{
                            userView.getUserButton().setText("Witaj " + library.getCurrentlyLoggedUser().getName() + "!");
                            currentView = userView;
                            currentView.setVisible(true);
                        }
                        loginView.getUsernameField().setText(null);
                        loginView.getPasswordField().setText(null);

                    }
                    i++;
                }
            } else {
                currentView.showErrorMessage("Podano błędną nazwę użytkownika lub hasło");
                loginView.getPasswordField().setText(null);
            }
    }

    /**
     * Changes view from login to registration view.
     */
    public void registerButtonLoginAction(){
        currentView.setVisible(false);
        currentView = registerView;
        currentView.setVisible(true);
    }

    /**
     * Creates new account for user depending on the parameters given by user.
     * If user is the first one creating an account it creates an administrator account.
     * If username or password is shorter than 3 or longer than 20 characters, an account with this
     * username already exists or password and cofirmPassword fields are not matching then user account
     * is not created and appropriate dialog window is displayed.
     */
    public void registerButtonRegisterAction(){
        String name = registerView.getUsernameField().getText();
        char[] password = registerView.getPasswordField().getPassword();
        char[] confirmPassword = registerView.getConfirmPasswordField().getPassword();

        if(name.length()<= 3 || password.length <= 3 || name.length()>= 20 || password.length >= 20){
            currentView.showErrorMessage("<html> Nazwa użytkownika oraz hasło musi składać się <br/> z przynajmniej 4  i maksymalnie 20 znaków </html>");
        }
        else {
            if (library.getNamesAndPasswords().containsKey(name)) {
                currentView.showErrorMessage("Użytkownik o tej nazwie już istnieje");
            } else if (!Arrays.equals(password, confirmPassword)) {
                currentView.showErrorMessage("Pola 'Hasło' oraz 'Powtórz hasło' różnią się od siebie");
            } else {
                currentView.setVisible(false);
                if (library.getAdmin() == null) {
                    currentView = addBooksView;
                    currentView.setVisible(true);
                    Administrator admin = new Administrator(name, password, library);
                } else {
                    currentView = loginView;
                    currentView.setVisible(true);
                    User user = new User(name, password, library);
                }
                registerView.getUsernameField().setText(null);
                registerView.getPasswordField().setText(null);
                registerView.getConfirmPasswordField().setText(null);
                currentView.setVisible(true);
            }
        }
    }

    /**
     * Checks whether currently logged user is an admin and if so it cancels all reminders. Afterwards it changes view
     * to the loginview.
     */

    public void logoutButtonAction(){
        UserView view = (UserView) currentView;
        if(library.getAdmin() == library.getCurrentlyLoggedUser()){
            library.getAdmin().cancelReminders();
        }
        currentView.setVisible(false);
        view.getMainPanel().removeAll();
        view.repaint();
        currentView.setVisible(false);
        currentView = loginView;
        currentView.setVisible(true);
    }

    /**
     * Changes view to the one that displays info about the Home Library app.
     */

    public void userButtonAction() {
        UserView view = (UserView) currentView;
        view.userButtonView();

    }
    /**
     * Changes the view to a view which displays list of books that user wants to read.
     */
    public void toReadBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksToRead();
        JButton button1 = view.getDeleteToReadButton();
        JButton button2 = view.getAddReadButton();
        JButton[] buttons = {button1, button2};
        view.selectBookView(books, buttons);
    }
    /**
     * Changes the view to a view which displays list of books that user read.
     */
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
    /**
     * Changes the view to a view which displays list of books that user rated.
     */
    public void ratedBooksButtonAction(){
        UserView view = (UserView) currentView;
        ArrayList<Book> books = library.getCurrentlyLoggedUser().getBooksRated();
        String name = library.getCurrentlyLoggedUser().getName();
        JButton button1= view.getDeleteRateButton();
        JButton[] buttons = {button1};
        view.selectRatedBookView(books, name,buttons);
    }

    /**
     * Changes the view to a view in which you can choose account to delete.
     */
    public void deleteAccountButtonAction(){
        AdminView view = (AdminView) currentView;
        view.deleteAccountView(getUsersTable());
    }

    /**
     * Deletes an account that was chosen in combobox. But before it asks the user whether is sure to do it.
     */
    public void confirmChoosingAccountButtonAction(){
        AdminView view = (AdminView) currentView;
        User user = (User)view.getUsersComboBox().getSelectedItem();
        if(user == null){
            view.showErrorMessage("Nie można usunąć konta bez podania użytkownika");
            return;
        }
        if(view.showConfirmingDeletingAccountDialog() == 0){
            library.getNamesAndPasswords().remove(user.getName());
            library.getAdmin().deleteUser(user, library.getUsers());
            view.showPlainMessage("Konto zostało usunięte","");
        }
        else
            view.showPlainMessage("Konto nie zostało usunięte","");
        view.resetMainPanel();

    }

    /**
     * Asks user what he wants to do with the reminder which time was up.
     * @param reminder reminder which time was up.
     */
    @Override
    public void reminderSendAction(Reminder reminder) {
        AdminView view = (AdminView) currentView;
        if(lastChosenAction != 0){
            view.setLastSelectedBook(reminder.getBorrowedBook());
            view.setLastSelectedReminder(reminder);
            lastChosenAction = view.reminderWasSendView(reminder.returnReminderMessage());
            switch (lastChosenAction){
                case 0-> postponeReturningBookButtonAction();
                case 1-> returnedBookButtonAction();
                case 2-> deleteReminderButtonAction();
            }
        }

    }
}