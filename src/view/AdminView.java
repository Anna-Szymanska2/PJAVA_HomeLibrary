package view;

import model.Book;
import model.Reminder;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * AdminView class enables to show views which only admin can see.
 */
public class AdminView extends UserView{
    private JButton remindersButton = new JButton("Przypomnienia");
    private JButton addBookButton = new JButton("Dodaj książkę");
    private JButton deleteAccountButton = new JButton("Usuń konto");
    private JButton borrowBookButton = new JButton("Pożycz");
    private JButton returnedBookButton = new JButton("Książkę zwrócono");
    private JButton deleteReminderButton = new JButton("<html>Usuń<br/>przypomnienie</html>");
    private JButton postponeReminderButton = new JButton("<html>Przedłuż<br/>przypomnienie</html>");
    private JButton postponeReturningBookButton = new JButton("<html>Przedłuż termin<br/> zwrotu</html>");

    private JButton confirmPostponingReturningBookButton = new JButton("Potwierdź");
    private JButton confirmBorrowingBookButton = new JButton("Potwierdź");
    private JButton confirmChoosingAccountButton = new JButton("Potwierdź");
    private JButton confirmAddingBookButton = new JButton("Potwierdź");
    private JComboBox<User> usersComboBox;
    private JComboBox<String> timeComboBox;
    private JCheckBox reminderCheckbox;
    private JTextField addBookTextFields[] = new JTextField[7];

    public JButton getConfirmAddingBookButton() {
        return confirmAddingBookButton;
    }

    public JButton getConfirmChoosingAccountButton() {
        return confirmChoosingAccountButton;
    }

    public Reminder getLastSelectedReminder() {
        return lastSelectedReminder;
    }

    public void setLastSelectedReminder(Reminder lastSelectedReminder) {
        this.lastSelectedReminder = lastSelectedReminder;
    }

    public JTextField[] getAddBookTextFields() {
        return addBookTextFields;
    }

    private Reminder lastSelectedReminder;



    public JButton getPostponeReturningBookButton() {
        return postponeReturningBookButton;
    }

    public JComboBox<User> getUsersComboBox() {
        return usersComboBox;
    }

    public JComboBox<String> getTimeComboBox() {
        return timeComboBox;
    }

    public JCheckBox getReminderCheckbox() {
        return reminderCheckbox;
    }

    public JButton getConfirmBorrowingBookButton() {
        return confirmBorrowingBookButton;
    }

    public JButton getBorrowBookButton() {
        return borrowBookButton;
    }

    public JButton getReturnedBookButton() {
        return returnedBookButton;
    }

    public JButton getDeleteReminderButton() {
        return deleteReminderButton;
    }

    public JButton getPostponeReminderButton() {
        return postponeReminderButton;
    }

    public JButton getRemindersButton() {
        return remindersButton;
    }

    public JButton getAddBookButton() {
        return addBookButton;
    }

    public JButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void initView(){
        super.initView();
        buttonsPanel.add(remindersButton);
        buttonsPanel.add(addBookButton);
        buttonsPanel.add(deleteAccountButton);
        buttonsPanel.remove(logoutButton);
        buttonsPanel.add(logoutButton);
        buttonsPanel.setLayout(new GridLayout(11,1));
        String[] time = {"tydzień", "2 tygodnie", "miesiąc", "2 miesiące"};
        timeComboBox = new JComboBox<>(time);
    }

    /**
     * Displays the view that enables to choose borrowing book details.
     * @param users array of users which is used to create combobox from which it is possible to choose borrower.
     */
    public void borrowBookView(User[] users) {
        String description = lastSelectedBook.returnLongDescription();
        displayLabelOnNorthOfMainPanel(description);
        JButton[] buttons = {getConfirmBorrowingBookButton()};
        setButtonsAtRight(buttons);

        JLabel chooseUserLabel = new JLabel("Wybierz osobę pożyczającą z listy lub napisz własną");
        flowPanel.add(chooseUserLabel);
        usersComboBox = new JComboBox<>( users);
        usersComboBox.setEditable(true);
        flowPanel.add(usersComboBox);
        choosingBorrowingTimeView();
        reminderCheckbox = new JCheckBox("Czy chcesz, żeby zostało ustawione przypomnienie?");
        reminderCheckbox.setPreferredSize(new Dimension(mainPanel.getWidth(), 15));
        flowPanel.add(reminderCheckbox);
        setVisible(true);
        repaint();

    }

    /**
     * Displays the view where you can write details of the book which is being added to the library.
     */
    public void addBookView(){
        resetMainPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel addBookPanel = new JPanel(new GridLayout(7,2));
        addBookPanel.setPreferredSize(new Dimension(500,200));
        JLabel titleLabel = new JLabel("Tytuł");
        JLabel authorLabel = new JLabel("Autor");
        JLabel pagesLabel = new JLabel("Liczba stron");
        JLabel publishYearLabel = new JLabel("Rok wydania");
        JLabel genreLabel = new JLabel("Gatunek");
        JLabel seriesLabel = new JLabel("Seria");
        JLabel seriesVolumeLabel = new JLabel("Tom");
        JLabel labels[] ={titleLabel, authorLabel, pagesLabel, publishYearLabel, genreLabel, seriesLabel, seriesVolumeLabel};
        mainPanel.add(addBookPanel);

        for(int i = 0; i < 7; i++){
            JTextField textField = new JTextField();
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            addBookPanel.add(labels[i]);
            addBookPanel.add(textField);
            addBookTextFields[i] = textField;
        }
        JButton[] buttons = {getConfirmAddingBookButton()};
        setButtonsAtRight(buttons);
        setVisible(true);
        repaint();
    }

    /**
     * Displays the view in which you can choose account to delete.
     * @param users array of users which is used to create combobox from which it is possible to choose which user's
     *              account will be deleted.
     */
    public void deleteAccountView(User[] users){
        JLabel chooseUserLabel = new JLabel("Wybierz użytkownika, którego konto chcesz usunąć");
        prepareFLowLayout();
        flowPanel.add(chooseUserLabel);
        usersComboBox = new JComboBox<>(users);
        flowPanel.add(usersComboBox);
        JButton[] buttons = {getConfirmChoosingAccountButton()};
        setButtonsAtRight(buttons);
        setVisible(true);
        repaint();

    }

    /**
     * Shows the confirmation dialog about deleting the account.
     * @return answer which indicates which button of confirmation dialog was chosen.
     */
    public int showConfirmingDeletingAccountDialog(){
        int answer = JOptionPane.showConfirmDialog(this, "Czy na pewno chesz usunąć to konto?", "", JOptionPane.YES_NO_CANCEL_OPTION );
        return answer;
    }

    /**
     * Displays the view in which it is possible to choose time.
     */
    public void choosingBorrowingTimeView(){
        JLabel chooseTimeLabel = new JLabel("<html>Wybierz okres czasu, na który książka ma zostać pożyczona, pamiętaj, " +
                "jeśli książka powinna <br/>zostać już zwrócona wybrany czas zostanie dodany do obecnej daty, jeśli nie, do" +
                " obecnej<br/> daty zwrotu<html/>");
        flowPanel.add(chooseTimeLabel);
        flowPanel.add(timeComboBox);
    }

    public JButton getConfirmPostponingReturningBookButton() {
        return confirmPostponingReturningBookButton;
    }

    /**
     * Displays the view in which you can choose postponing returning date of the book details.
     */
    public void postponeReturningBookView(){
        String description = lastSelectedBook.returnLongDescription();
        displayLabelOnNorthOfMainPanel(description);
        JButton[] buttons = {getConfirmPostponingReturningBookButton()};
        setButtonsAtRight(buttons);
        choosingBorrowingTimeView();
        setVisible(true);
        repaint();
    }

    /**
     * Returns JList of reminders.
     * @param reminders reminders that are added to JList.
     * @return JList of reminders.
     */
    private JList<Reminder> getReminderJList(ArrayList<Reminder> reminders) {
        JList<Reminder> list = new JList<>();
        DefaultListModel <Reminder> model = new DefaultListModel<>();
        list.setModel(model);
        model.addAll(reminders);
        list.setVisibleRowCount(30);
        mainPanel.removeAll();
        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(mainPanel.getWidth() - 20, mainPanel.getHeight()));
        mainPanel.add(scroll);
        setVisible(true);
        repaint();
        return list;
    }

    /**
     * Displays the view of the list of reminders.
     * @param reminders reminders added to the list.
     * @param buttons array of buttons which are displayed after choosing a reminder.
     */
    public void selectReminderView(ArrayList<Reminder> reminders, JButton []buttons){
        JList<Reminder> list = getReminderJList(reminders);

        list.addListSelectionListener(e -> {
            lastSelectedReminder = (Reminder)list.getSelectedValue();
            String description = lastSelectedReminder.getBorrowedBook().returnLongDescription();
            displayLabelOnNorthOfMainPanel(description);
            if(buttons.length != 0)
                setButtonsAtRight(buttons);
            setVisible(true);
            repaint();

        });

    }

    /**
     * Displays the dialog of choices what user can do with sent reminder.
     * @param message
     * @return
     */
    public int reminderWasSendView(String message){
        String [] responses = {"Chcę przedłużyć czas na zwrot książki", "Książka została zwrócona", "Usuń powiadomienie"};
        return (JOptionPane.showOptionDialog(null,
                message,
                "",
                0,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                responses,
                2));
    }

    public void setLastSelectedBook(Book book) {
        lastSelectedBook = book;
    }
}
