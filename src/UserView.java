import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class UserView extends View {
    private Book lastSelectedBook;
    protected JButton logoutButton = new JButton("Wyloguj");
    private JButton userButton = new JButton("Witaj użytkowniku");
    private JButton findBookButton = new JButton("Znajdź książkę");
    private JButton readBooksButton = new JButton("Przeczytane");
    private JButton toReadBooksButton = new JButton("Do przeczytania");
    private JButton ratedBooksButton = new JButton("Ocenione");
    private JButton borrowedBooksButton = new JButton("Pożyczone");
    private JButton recommendBookButton = new JButton("Poleć książkę");
    private JButton addToReadButton = new JButton("<html>Dodaj do listy<br/>do przeczytania</html>");
    private JButton deleteToReadButton = new JButton("<html>Usuń z listy<br/>do przeczytania</html>");
    private JButton addReadButton = new JButton("<html>Dodaj do listy<br/>przeczytanych</html>");
    private JButton deleteReadButton = new JButton("<html>Usuń z listy<br/>przeczytanych</html>");
    private JButton deleteRateButton = new JButton("Usuń ocenę");
    private JButton addRateButton = new JButton("Oceń");
    protected JPanel buttonsPanel = new JPanel(new GridLayout(8, 1));
    JPanel mainPanel = new JPanel();

    public Book getLastSelectedBook() {
        return lastSelectedBook;
    }

    public JButton getDeleteRateButton() {
        return deleteRateButton;
    }

    public JButton getAddRateButton() {
        return addRateButton;
    }

    public JButton getAddReadButton() {
        return addReadButton;
    }

    public JButton getRecommendBookButton() {
        return recommendBookButton;
    }

    public JButton getAddToReadButton() {
        return addToReadButton;
    }

    public JButton getDeleteToReadButton() {
        return deleteToReadButton;
    }

    public JButton getDeleteReadButton() {
        return deleteReadButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getUserButton() {
        return userButton;
    }

    public JButton getFindBookButton() {
        return findBookButton;
    }

    public JButton getReadBooksButton() {
        return readBooksButton;
    }

    public JButton getToReadBooksButton() {
        return toReadBooksButton;
    }

    public JButton getRatedBooksButton() {
        return ratedBooksButton;
    }

    public JButton getBorrowedBooksButton() {
        return borrowedBooksButton;
    }

    public UserView(){

    }

    public void initView(){
        //setVisible(true);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        buttonsPanel.add(userButton);
        buttonsPanel.add(findBookButton);
        buttonsPanel.add(readBooksButton);
        buttonsPanel.add(toReadBooksButton);
        buttonsPanel.add(ratedBooksButton);
        buttonsPanel.add(borrowedBooksButton);
        buttonsPanel.add(recommendBookButton);
        buttonsPanel.add(logoutButton);
        buttonsPanel.setPreferredSize(new Dimension(150,600));
        add(buttonsPanel,BorderLayout.WEST);
        mainPanel.setPreferredSize(new Dimension(550,600));
        add(mainPanel,BorderLayout.CENTER);


    }

    public void addingDeletingBookMessage(String message, String title){
        JOptionPane.showMessageDialog(this, message, title,JOptionPane.INFORMATION_MESSAGE );
    }

    public void userButtonView(String labelText){
        getMainPanel().removeAll();
        getMainPanel().add(new JLabel(labelText));
        setVisible(true);
        repaint();
    }

    public void selectBookView(ArrayList<Book> books, JButton button1, JButton button2, JButton button3){
        JList<Book> list = new JList<Book>();
        DefaultListModel <Book> model = new DefaultListModel<>();
        list.setModel(model);
        model.addAll(books);
        list.setVisibleRowCount(30);
        list.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                lastSelectedBook = (Book)list.getSelectedValue();
                JLabel myLabel = new JLabel(lastSelectedBook.returnLongDescription());
                JPanel bottomPanel = new JPanel(new GridLayout(1,3));
                bottomPanel.add(button1);
                bottomPanel.add(button2);
                bottomPanel.add(button3);
                bottomPanel.setPreferredSize(new Dimension(150,100));
                getMainPanel().removeAll();
                getMainPanel().setLayout(new BorderLayout());
                getMainPanel().add(myLabel,BorderLayout.NORTH);
                getMainPanel().add(bottomPanel,BorderLayout.SOUTH);
                setVisible(true);
                repaint();
            }
        });
        getMainPanel().removeAll();
        JScrollPane scroll = new JScrollPane(list);
        getMainPanel().add(scroll);
        setVisible(true);
        repaint();

    }



    public static void main (String []arg){

        UserView view = new UserView();
        view.initView();
    }

}
