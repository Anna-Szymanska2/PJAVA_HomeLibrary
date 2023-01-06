import javax.swing.*;

public abstract class View extends JFrame {

    public View(){
        setTitle("Domowa biblioteka");
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
