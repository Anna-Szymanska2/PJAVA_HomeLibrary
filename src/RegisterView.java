import javax.swing.*;
import java.awt.*;

public class RegisterView extends View{
    private JLabel usernameLabel = new JLabel("Użytkownik: ");
    private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel("Hasło: ");
    private JPasswordField passwordField = new JPasswordField();
    private JLabel confirmPasswordLabel = new JLabel("Powtórz hasło: ");
    private JPasswordField confirmPasswordField = new JPasswordField();
    private JPanel formPanel = new JPanel(new GridLayout(3, 2));
    private JPanel buttonPanel = new JPanel(new FlowLayout());
    private JButton registerButton = new JButton("Zarejestruj");

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
    public JLabel getConfirmPasswordLabel() {
        return confirmPasswordLabel;
    }
    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }
    public JPanel getFormPanel() {
        return formPanel;
    }
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
    public JButton getRegisterButton() {
        return registerButton;
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
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        add(formPanel, BorderLayout.CENTER);
        buttonPanel.add(registerButton);
        add(buttonPanel,BorderLayout.SOUTH);
    }
}
