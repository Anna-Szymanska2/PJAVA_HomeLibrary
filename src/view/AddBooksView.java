package view;

import javax.swing.*;
import java.awt.*;

/**
 * AddBooksView class enables to choose file with books that will be the base for library.
 */
public class AddBooksView extends View {
    private JButton selectBooksButton = new JButton("Wybierz plik z książkami");
    private JFileChooser fileChooser = new JFileChooser();

    public JButton getSelectBooksButton() {
        return selectBooksButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void initView() {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,1));
        add(new JLabel("Cześć, zostałeś administratorem domowej biblioteczki!", SwingConstants.CENTER));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectBooksButton.setPreferredSize(new Dimension(200,100));
        selectBooksButton.setFocusable(false);
        add(selectBooksButton);
        buttonPanel.add(selectBooksButton);
        add(buttonPanel);

    }

}
