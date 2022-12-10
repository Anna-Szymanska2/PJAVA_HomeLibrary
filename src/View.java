import javax.swing.*;

public abstract class View extends JFrame {

    public View(){
        setTitle("Domowa biblioteka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public abstract void initView();
}
