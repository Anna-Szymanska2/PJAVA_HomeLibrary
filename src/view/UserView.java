package view;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
    private JButton filterButton = new JButton("Filtruj");
    private JComboBox authorComboBox;
    private JComboBox genreComboBox;
    private JSpinner ratingMinSpinner;
    private JSpinner ratingMaxSpinner;
    private JSpinner pageCountMinSpinner;
    private JSpinner pageCountMaxSpinner;
    private JSpinner volumesMinSpinner;
    private JSpinner volumesMaxSpinner;
    private JComboBox publishYearMinBox;
    private JComboBox publishYearMaxBox;

    private JSlider ratingSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
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
    public JButton getFilterButton() {
        return filterButton;
    }
    public JComboBox getAuthorComboBox() {
        return authorComboBox;
    }
    public JComboBox getGenreComboBox() {
        return genreComboBox;
    }
    public JSpinner getRatingMinSpinner() {
        return ratingMinSpinner;
    }
    public JSpinner getRatingMaxSpinner() {
        return ratingMaxSpinner;
    }
    public JSpinner getPageCountMinSpinner() {
        return pageCountMinSpinner;
    }
    public JSpinner getPageCountMaxSpinner() {
        return pageCountMaxSpinner;
    }
    public JSpinner getVolumesMinSpinner() {
        return volumesMinSpinner;
    }
    public JSpinner getVolumesMaxSpinner() {
        return volumesMaxSpinner;
    }
    public JComboBox getPublishYearMinBox() {
        return publishYearMinBox;
    }
    public JComboBox getPublishYearMaxBox() {
        return publishYearMaxBox;
    }
    public JSlider getRatingSlider() {
        return ratingSlider;
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



    public void userButtonView(){
        String labelText = "<html>Aplikacja Home Library służy do wygodnego zarządzania domowym księgozbiorem.<br/>" +
                "Twórcy:<br/>Dorota Wlazło<br/>Anna Szymańska</html>";
        mainPanel.removeAll();
        JLabel myLabel = new JLabel(labelText);
        prepareFLowLayout();
        flowPanel.add(myLabel);
        setVisible(true);
        repaint();
    }

    public void displayLabelOnNorthOfMainPanel(String description){
        JLabel myLabel = new JLabel(description);
        myLabel.setPreferredSize(new Dimension(getMainPanel().getWidth(), 200));
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
        resetMainPanel();
        JList<Book> list = getBookJList(books, mainPanel.getHeight());

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
    public void selectRatedBookView(ArrayList<Book> books,String name, JButton []buttons){
        resetMainPanel();
        JList<Book> list = getBookJList(books,mainPanel.getHeight() );

        list.addListSelectionListener(e -> {
            lastSelectedBook = (Book)list.getSelectedValue();
            //JLabel myLabel = new JLabel(lastSelectedBook.returnLongDescription());
            String description = lastSelectedBook.returnLongDescription();
            displayLabelOnNorthOfMainPanel(description);
            JLabel ratingLabel = new JLabel("Oceniłeś tę książkę na: " + lastSelectedBook.getRatings().get(name), SwingConstants.CENTER);
            ratingLabel.setSize(new Dimension(550/2,50));
            flowPanel.add(ratingLabel,BorderLayout.SOUTH);
            if(buttons.length != 0)
                setButtonsAtRight(buttons);
            setVisible(true);
            repaint();

        });

    }

    public void selectReadBookView(ArrayList<Book> books, ArrayList<Book> booksRated,String name,JButton []buttons){
        resetMainPanel();
        JList<Book> list = getBookJList(books, mainPanel.getHeight());

        list.addListSelectionListener(e -> {
            lastSelectedBook = (Book)list.getSelectedValue();
            //JLabel myLabel = new JLabel(lastSelectedBook.returnLongDescription());
            String description = lastSelectedBook.returnLongDescription();
            displayLabelOnNorthOfMainPanel(description);
            if(booksRated.contains(lastSelectedBook)){
                buttons[1] = getDeleteRateButton();
                JLabel ratingLabel = new JLabel("Oceniłeś tę książkę na: " + lastSelectedBook.getRatings().get(name), SwingConstants.CENTER);
                ratingLabel.setSize(new Dimension(550/2,50));
                flowPanel.add(ratingLabel,BorderLayout.SOUTH);
            }else{
                ratingSlider.setMinorTickSpacing(1);
                ratingSlider.setMajorTickSpacing(5);
                ratingSlider.setPaintTicks(true);
                ratingSlider.setPaintLabels(true);
                ratingSlider.setSize(new Dimension(550/2,50));
                flowPanel.add(ratingSlider,BorderLayout.SOUTH);
            }
            if(buttons.length != 0)
                setButtonsAtRight(buttons);
            setVisible(true);
            repaint();

        });

    }

    public void findBookView(ArrayList<Book> books, JButton []buttons){
        resetMainPanel();
        mainPanel.setLayout(new FlowLayout());
        JPanel filterBooksPanel = new JPanel(new GridLayout(5,4));
        filterBooksPanel.setPreferredSize(new Dimension(500,200));
        JLabel authorLabel = new JLabel("Autor: ", SwingConstants.CENTER);
        JLabel genreLabel = new JLabel("Gatunek: ", SwingConstants.CENTER);
        JLabel publishYearMinLabel = new JLabel("Wydana po: ", SwingConstants.CENTER);
        JLabel publishYearMaxLabel = new JLabel("Wydana przed: ", SwingConstants.CENTER);
        JLabel ratingMinLabel = new JLabel("Min ocena: ", SwingConstants.CENTER);
        JLabel ratingMaxLabel = new JLabel("Max ocena: ", SwingConstants.CENTER);
        JLabel pageCountMinLabel = new JLabel("Min ilość stron: ", SwingConstants.CENTER);
        JLabel pageCountMaxLabel = new JLabel("Max ilość stron: ", SwingConstants.CENTER);
        JLabel volumesMinLabel = new JLabel("Min ilość tomów: ", SwingConstants.CENTER);
        JLabel volumesMaxLabel = new JLabel("Max ilość tomów: ", SwingConstants.CENTER);

        ArrayList<String> listOfAuthors = new ArrayList<>();
        listOfAuthors.add("-");
        for(Book book:books) {
            listOfAuthors.add(book.getAuthor());
        }
        List<String> noDuplicates = listOfAuthors.stream().distinct().collect(Collectors.toList());
        Vector<String> noDuplicatesV = new Vector<>(noDuplicates);
        authorComboBox = new JComboBox(noDuplicatesV);

        ArrayList<String> listOfGenres = new ArrayList<>();
        listOfGenres.add("-");
        for(Book book:books) {
            listOfGenres.add(book.getGenre());
        }
        List<String> noDuplicates2 = listOfGenres.stream().distinct().collect(Collectors.toList());
        Vector<String> noDuplicatesV2 = new Vector<>(noDuplicates2);
        genreComboBox = new JComboBox(noDuplicatesV2);

        Integer years[]={0,1960,1970,1980,1990,2000,2005,2010,2015,2020,2021,2022};
        publishYearMinBox = new JComboBox(years);
        publishYearMaxBox = new JComboBox(years);

        SpinnerModel value1 = new SpinnerNumberModel(0,0,10,1);
        ratingMinSpinner = new JSpinner(value1);
        SpinnerModel value2 = new SpinnerNumberModel(0,0,10,1);
        ratingMaxSpinner = new JSpinner(value2);
        SpinnerModel value3 = new SpinnerNumberModel(0,0,1200,100);
        pageCountMinSpinner = new JSpinner(value3);
        SpinnerModel value4 = new SpinnerNumberModel(0,0,1200,100);
        pageCountMaxSpinner = new JSpinner(value4);
        SpinnerModel value5 = new SpinnerNumberModel(0,0,12,1);
        volumesMinSpinner = new JSpinner(value5);
        SpinnerModel value6 = new SpinnerNumberModel(0,0,12,1);
        volumesMaxSpinner = new JSpinner(value6);

        filterBooksPanel.add(authorLabel);
        filterBooksPanel.add(authorComboBox);
        filterBooksPanel.add(genreLabel);
        filterBooksPanel.add(genreComboBox);
        filterBooksPanel.add(publishYearMinLabel);
        filterBooksPanel.add(publishYearMinBox);
        filterBooksPanel.add(publishYearMaxLabel);
        filterBooksPanel.add(publishYearMaxBox);
        filterBooksPanel.add(ratingMinLabel);
        filterBooksPanel.add(ratingMinSpinner);
        filterBooksPanel.add(ratingMaxLabel);
        filterBooksPanel.add(ratingMaxSpinner);
        filterBooksPanel.add(pageCountMinLabel);
        filterBooksPanel.add(pageCountMinSpinner);
        filterBooksPanel.add(pageCountMaxLabel);
        filterBooksPanel.add(pageCountMaxSpinner);
        filterBooksPanel.add(volumesMinLabel);
        filterBooksPanel.add(volumesMinSpinner);
        filterBooksPanel.add(volumesMaxLabel);
        filterBooksPanel.add(volumesMaxSpinner);

        filterButton.setPreferredSize(new Dimension(550/4,25));

        JPanel searchBooksPanel = new JPanel(new GridLayout(1,2));
        searchBooksPanel.setPreferredSize(new Dimension(500,25));
        JLabel searchLabel = new JLabel("Wyszukaj: ", SwingConstants.CENTER);
        JTextField searchField = new JTextField();
        searchBooksPanel.add(searchLabel);
        searchBooksPanel.add(searchField);
        mainPanel.add(filterBooksPanel);
        mainPanel.add(filterButton);
        mainPanel.add(searchBooksPanel);
        JList<Book> list = getBookJList(books, 275);
        //list.setSize(new Dimension(550,350));


       /* mainPanel.add(list);
        JScrollPane scroll = new JScrollPane(list);
        scroll.setSize(new Dimension(550,350));
        mainPanel.add(scroll);*/
        setVisible(true);
        repaint();

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

    private JList<Book> getBookJList(ArrayList<Book> books,int scrollHeight) {
        JList<Book> list = new JList<>();
        DefaultListModel <Book> model = new DefaultListModel<>();
        list.setModel(model);
        //list.setPreferredSize(new Dimension(mainPanel.getWidth() - 20, mainPanel.getHeight()));
        model.addAll(books);
        list.setVisibleRowCount(30);
        //mainPanel.removeAll();
        JScrollPane scroll = new JScrollPane(list);
        mainPanel.add(scroll);
        scroll.setPreferredSize(new Dimension(mainPanel.getWidth() - 20,scrollHeight));
        setVisible(true);
        repaint();
        return list;
    }

    public static void main (String []arg){

        UserView view = new UserView();
        view.initView();
    }

}
