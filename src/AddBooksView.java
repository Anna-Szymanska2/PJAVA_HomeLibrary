import javax.swing.*;
import java.awt.*;

public class AddBooksView extends View{
    private JButton selectBooksButton = new JButton("Wybierz plik z książkami");
    private JFileChooser fileChooser = new JFileChooser();

    public JButton getSelectBooksButton() {
        return selectBooksButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    @Override
    public void initView() {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,1));
        //JPanel labelPanel = new JPanel(new BorderLayout());
        //labelPanel.add(new JLabel("Cześć, zostałeś administratorem domowej biblioteczki!", SwingConstants.CENTER), BorderLayout.CENTER);
        add(new JLabel("Cześć, zostałeś administratorem domowej biblioteczki!", SwingConstants.CENTER));
        //add((labelPanel));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectBooksButton.setPreferredSize(new Dimension(200,100));
        selectBooksButton.setFocusable(false);
        add(selectBooksButton);
        buttonPanel.add(selectBooksButton);
        add(buttonPanel);

    }

    public static void main (String []arg){
        AddBooksView view = new AddBooksView();
        view.initView();
        view.setVisible(true);
    }
}
