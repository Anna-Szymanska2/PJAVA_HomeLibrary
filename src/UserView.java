import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserView extends View {
    protected Book lastSelectedBook;
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
    protected JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    protected JPanel mainPanel = new JPanel();

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
        mainPanel.removeAll();
        mainPanel.add(new JLabel(labelText));
        setVisible(true);
        repaint();
    }

    public void displayLabelOnNorthOfMainPanel(String description){
        JLabel myLabel = new JLabel(description);
        prepareFLowLayout();
        myLabel.setPreferredSize(new Dimension(getMainPanel().getWidth(), 150));
        flowPanel.add(myLabel);

    }

    public void prepareFLowLayout(){
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        flowPanel.removeAll();
        //JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(flowPanel,BorderLayout.CENTER);
    }

    public void setButtonsAtRight(JButton []buttons){
       /* getMainPanel().removeAll();
        getMainPanel().setLayout(new BorderLayout());
        getMainPanel().add(myLabel,BorderLayout.NORTH);*/
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonsAtRightPanel = new JPanel(new GridLayout(buttons.length,1));
        bottomPanel.add(buttonsAtRightPanel, BorderLayout.EAST);
        for(JButton button: buttons)
            buttonsAtRightPanel.add(button);
        buttonsAtRightPanel.setPreferredSize(new Dimension(150,100));
        bottomPanel.setPreferredSize(new Dimension(150,50*buttons.length));
        getMainPanel().add(bottomPanel,BorderLayout.SOUTH);
    }

    public void selectBookView(ArrayList<Book> books, JButton []buttons){
        JList<Book> list = getBookJList(books);

        list.addListSelectionListener(e -> {
            lastSelectedBook = (Book)list.getSelectedValue();
            //JLabel myLabel = new JLabel(lastSelectedBook.returnLongDescription());
            String description = lastSelectedBook.returnLongDescription();
            displayLabelOnNorthOfMainPanel(description);
            if(buttons.length != 0)
                setButtonsAtRight(buttons);
            setVisible(true);
            repaint();

        });

    }

    public void resetMainPanel(){
        mainPanel.removeAll();
        setVisible(true);
        repaint();
    }

    private JList<Book> getBookJList(ArrayList<Book> books) {
        JList<Book> list = new JList<>();
        DefaultListModel <Book> model = new DefaultListModel<>();
        list.setModel(model);
        list.setPreferredSize(new Dimension(mainPanel.getWidth() - 20, mainPanel.getHeight()));
        model.addAll(books);
        list.setVisibleRowCount(30);
        mainPanel.removeAll();
        JScrollPane scroll = new JScrollPane(list);
        mainPanel.add(scroll);
        setVisible(true);
        repaint();
        return list;
    }


    public static void main (String []arg){

        UserView view = new UserView();
        view.initView();
    }

}
