import javax.swing.*;
import java.awt.*;

public class LoginView extends View {

    private JButton  loginButton = new JButton("Zaloguj");
    private JButton  registerButton = new JButton("Zarejestruj");
    private JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
    private JLabel usernameLabel = new JLabel("Użytkownik: ", SwingConstants.CENTER);
    private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel("Hasło: ", SwingConstants.CENTER);
    private JPasswordField passwordField = new JPasswordField();
    private JPanel formPanel = new JPanel(new GridLayout(2, 2));

    public JButton getLoginButton() {
        return loginButton;
    }
    public JLabel getUsernameLabel() {
        return usernameLabel;
    }
    public JTextField getUsernameField() {
        return usernameField;
    }
    public JLabel getPasswordLabel() {
        return passwordLabel;
    }
    public JPasswordField getPasswordField() {
        return passwordField;
    }
    public JPanel getFormPanel() {
        return formPanel;
    }
    public JButton getRegisterButton() {
        return registerButton;
    }
    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    @Override
    public void initView() {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        add(formPanel, BorderLayout.CENTER);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel,BorderLayout.SOUTH);
    }
}
