import javax.swing.*;

public class LoginView extends View {

    private JButton  loginButton = new JButton("Zaloguj");

    public JButton getLoginButton() {
        return loginButton;
    }

    @Override
    public void initView() {
        setSize(500, 300);
        setLocationRelativeTo(null);
        add(loginButton);
    }
}
