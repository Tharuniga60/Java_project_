import app.dao.UserDAO;
import app.model.User;
import app.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField tfUser;
    private JPasswordField pfPass;
    private final UserDAO userDAO = new UserDAO(); // corrected variable name

    public LoginFrame() {
        setTitle("Login - CodingShare");
        setSize(400, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        p.add(new JLabel("Username:"), c);
        c.gridx = 1;
        tfUser = new JTextField(18);
        p.add(tfUser, c);

        c.gridx = 0; c.gridy = 1;
        p.add(new JLabel("Password:"), c);
        c.gridx = 1;
        pfPass = new JPasswordField(18);
        p.add(pfPass, c);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        c.gridx = 0; c.gridy = 2;
        p.add(btnLogin, c);
        c.gridx = 1;
        p.add(btnRegister, c);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        btnRegister.addActionListener(a -> doRegister());

        add(p);
    }

    private void doLogin() {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password");
            return;
        }

        User u = userDAO.findByUsername(user);
        if (u != null && pass.equals(u.getPassword())) {
            SwingUtilities.invokeLater(() -> {
                MainFrame mf = new MainFrame(u);
                mf.setVisible(true);
            });
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }

    private void doRegister() {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill both fields");
            return;
        }

        User existing = userDAO.findByUsername(user);
        if (existing != null) {
            JOptionPane.showMessageDialog(this, "Username already exists");
            return;
        }

        User u = new User();
        u.setUsername(user);
        u.setPassword(pass);
        u.setFullName(user);

        boolean ok = userDAO.create(u);
        if (ok)
            JOptionPane.showMessageDialog(this, "Registered successfully. You can now login.");
        else
            JOptionPane.showMessageDialog(this, "Registration failed. Try again.");
    }

    // Entry point for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
