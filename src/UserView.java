import javax.swing.*;
import java.awt.*;

public class UserView extends View {
    protected JButton logoutButton = new JButton("Wyloguj");
    private JButton userButton = new JButton("Witaj użytkowniku");
    private JButton findBookButton = new JButton("Znajdź książkę");
    private JButton readBooksButton = new JButton("Przeczytane");
    private JButton toReadBooksButton = new JButton("Do przeczytania");
    private JButton markedBooksButton = new JButton("Ocenione");
    private JButton borrowedBooksButton = new JButton("Pożyczone");
    protected JPanel buttonsPanel = new JPanel(new GridLayout(7, 1));
    JPanel mainPanel = new JPanel();

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

    public JButton getMarkedBooksButton() {
        return markedBooksButton;
    }

    public JButton getBorrowedBooksButton() {
        return borrowedBooksButton;
    }

    public UserView(){

    }

    public void initView(){
        //setVisible(true);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        buttonsPanel.add(userButton);
        buttonsPanel.add(findBookButton);
        buttonsPanel.add(readBooksButton);
        buttonsPanel.add(toReadBooksButton);
        buttonsPanel.add(markedBooksButton);
        buttonsPanel.add(borrowedBooksButton);
        buttonsPanel.add(logoutButton);
        buttonsPanel.setPreferredSize(new Dimension(150,100));
        add(buttonsPanel,BorderLayout.WEST);
        mainPanel.setPreferredSize(new Dimension(100,100));
        add(mainPanel,BorderLayout.CENTER);


    }

    public void userButtonView(String labelText){
        getMainPanel().removeAll();
        getMainPanel().add(new JLabel(labelText));
        setVisible(true);
        repaint();
    }



    public static void main (String []arg){

        UserView view = new UserView();
        view.initView();
    }

}
