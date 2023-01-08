import javax.swing.*;
import java.awt.*;

public abstract class View extends JFrame {

    public View(){
        setTitle("Domowa biblioteka");
        Image icon = Toolkit.getDefaultToolkit().getImage("Icon.png");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public abstract void initView();
    public void showPlainMessage(String message, String title){
        JOptionPane.showMessageDialog(this, message, title,JOptionPane.INFORMATION_MESSAGE );
    }
    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message,  "Error", JOptionPane.ERROR_MESSAGE);
    }

}
