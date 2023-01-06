import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;

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
    private JComboBox<User> usersComboBox;
    private JComboBox<String> timeComboBox;
    private JCheckBox reminderCheckbox;

    public JButton getConfirmChoosingAccountButton() {
        return confirmChoosingAccountButton;
    }

    public Reminder getLastSelectedReminder() {
        return lastSelectedReminder;
    }

    public void setLastSelectedReminder(Reminder lastSelectedReminder) {
        this.lastSelectedReminder = lastSelectedReminder;
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
    public int showConfirmingDeletingAccountDialog(){
        int answer = JOptionPane.showConfirmDialog(null, "Czy na pewno chesz usunąć to konto?", "", JOptionPane.YES_NO_CANCEL_OPTION );
        return answer;
    }
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

    public void postponeReturningBookView(){
        String description = lastSelectedBook.returnLongDescription();
        displayLabelOnNorthOfMainPanel(description);
        JButton[] buttons = {getConfirmPostponingReturningBookButton()};
        setButtonsAtRight(buttons);
        choosingBorrowingTimeView();
        setVisible(true);
        repaint();
    }
    private JList<Reminder> getReminderJList(ArrayList<Reminder> reminders) {
        JList<Reminder> list = new JList<>();
        DefaultListModel <Reminder> model = new DefaultListModel<>();
        list.setModel(model);
        //list.setPreferredSize(new Dimension(mainPanel.getWidth() - 20, mainPanel.getHeight()));
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

    public void selectReminderView(ArrayList<Reminder> books, JButton []buttons){
        JList<Reminder> list = getReminderJList(books);

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

    public static void main (String []arg) {
        AdminView view = new AdminView();
        view.initView();
        view.setVisible(true);
    }


    public void setLastSelectedBook(Book book) {
        lastSelectedBook = book;
    }
}
