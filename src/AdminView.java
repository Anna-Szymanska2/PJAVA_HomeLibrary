import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdminView extends UserView{
    private JButton reminderButton = new JButton("Przypomnienia");
    private JButton addBookButton = new JButton("Dodaj książkę");
    private JButton deleteAccountButton = new JButton("Usuń konto");
    private JButton borrowBookButton = new JButton("Pożycz");
    private JButton returnedBookButton = new JButton("Książkę zwrócono");
    private JButton deleteReminderButton = new JButton("Usuń powiadomienie");
    private JButton postponeReminderButton = new JButton("Przedłuż powiadomienie");
    private JButton confirmBorrowingBookButton = new JButton("Potwierdź");
    private JComboBox<User> usersComboBox;
    private JComboBox<String> timeComboBox;
    private JCheckBox reminderCheckbox;

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

    public JButton getReminderButton() {
        return reminderButton;
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
        buttonsPanel.add(reminderButton);
        buttonsPanel.add(addBookButton);
        buttonsPanel.add(deleteAccountButton);
        buttonsPanel.remove(logoutButton);
        buttonsPanel.add(logoutButton);
        buttonsPanel.setLayout(new GridLayout(11,1));
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
        JLabel chooseTimeLabel = new JLabel("Wybierz okres czasu, na który książka ma zostać pożyczona");
        flowPanel.add(chooseTimeLabel);
        String[] time = {"tydzień", "2 tygodnie", "miesiąc", "2 miesiące"};
        timeComboBox = new JComboBox<>(time);
        flowPanel.add(timeComboBox);
        reminderCheckbox = new JCheckBox("Czy chcesz, żeby zostało ustawione powiadomienie?");
        flowPanel.add(reminderCheckbox);
        setVisible(true);
        repaint();


    }

    public static void main (String []arg) {
        AdminView view = new AdminView();
        view.initView();
        view.setVisible(true);
    }


}
