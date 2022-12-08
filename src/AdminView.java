import javax.swing.*;
import java.awt.*;

public class AdminView extends UserView{
    private JButton reminderButton = new JButton("Przypomnienia");
    private JButton addBookButton = new JButton("Dodaj książkę");
    private JButton deleteAccountButton = new JButton("Usuń konto");


    @Override
    public void initView(){
        super.initView();
        buttonsPanel.add(reminderButton);
        buttonsPanel.add(addBookButton);
        buttonsPanel.add(deleteAccountButton);
        buttonsPanel.remove(logoutButton);
        buttonsPanel.add(logoutButton);
        buttonsPanel.setLayout(new GridLayout(10,1));
    }

    public static void main (String []arg){
        AdminView view = new AdminView();
        view.initView();
    }


}
