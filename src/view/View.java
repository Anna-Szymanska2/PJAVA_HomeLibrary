package view;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create application main frame.
 * It's a parent class for other view classes.
 */
public abstract class View extends JFrame {

    public View(){
        setTitle("Domowa biblioteka");
        Image icon = Toolkit.getDefaultToolkit().getImage("Icon.png");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public abstract void initView();

    /**
     * Shows message dialog on screen.
     *
     * @param message
     * @param title
     */
    public void showPlainMessage(String message, String title){
        JOptionPane.showMessageDialog(this, message, title,JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Shows error message dialog on screen.
     *
     * @param message
     */
    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message,  "Error", JOptionPane.ERROR_MESSAGE);
    }

}
