package app.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import app.dao.UserDAO;
import app.model.User;

public class LoginFrame extends JFrame {
    private JTextField tfLoginUser;
    private JPasswordField pfLoginPass;
    private JTextField tfRegUser;
    private JPasswordField pfRegPass;
    private JTextField tfRegName;
    private UserDAO userDAO = new UserDAO();
    private CardLayout cardLayout;
    private JPanel cards;

    public LoginFrame() {
        setTitle("Resource Manager");
        setSize(520, 380);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIUtils.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel title = new JLabel("Resource Manager");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitle = new JLabel("Login or register to manage your resources");
        subtitle.setForeground(new Color(224, 224, 224));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);
        root.add(header, BorderLayout.NORTH);

        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);
        cards.setOpaque(false);

        cards.add(createLoginPanel(), "login");
        cards.add(createRegisterPanel(), "register");
        root.add(cards, BorderLayout.CENTER);

        JPanel switchBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        switchBar.setOpaque(false);
        JButton btnSwitchToRegister = UIUtils.createButton("Create Account", UIUtils.PRIMARY_DARK);
        JButton btnSwitchToLogin = UIUtils.createButton("Back to Login", UIUtils.PRIMARY_DARK);
        btnSwitchToRegister.addActionListener(e -> cardLayout.show(cards, "register"));
        btnSwitchToLogin.addActionListener(e -> cardLayout.show(cards, "login"));
        switchBar.add(btnSwitchToRegister);
        switchBar.add(btnSwitchToLogin);
        root.add(switchBar, BorderLayout.SOUTH);

        add(root);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 18, 10, 18);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel heading = new JLabel("Welcome Back");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(UIUtils.PRIMARY_DARK);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(heading, c);

        c.gridy++;
        panel.add(new JLabel("Username"), c);
        c.gridy++;
        tfLoginUser = new JTextField();
        panel.add(tfLoginUser, c);

        c.gridy++;
        panel.add(new JLabel("Password"), c);
        c.gridy++;
        pfLoginPass = new JPasswordField();
        panel.add(pfLoginPass, c);

        c.gridy++;
        JButton btnLogin = UIUtils.createButton("Login", UIUtils.PRIMARY);
        panel.add(btnLogin, c);
        btnLogin.addActionListener(e -> doLogin());

        c.gridy++;
        JLabel note = new JLabel("Securely sign in and continue to your dashboard.");
        note.setForeground(new Color(100, 100, 100));
        panel.add(note, c);

        return wrapCard(panel);
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 18, 10, 18);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel heading = new JLabel("Create New Account");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(UIUtils.PRIMARY_DARK);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(heading, c);

        c.gridy++;
        panel.add(new JLabel("Full Name"), c);
        c.gridy++;
        tfRegName = new JTextField();
        panel.add(tfRegName, c);

        c.gridy++;
        panel.add(new JLabel("Username"), c);
        c.gridy++;
        tfRegUser = new JTextField();
        panel.add(tfRegUser, c);

        c.gridy++;
        panel.add(new JLabel("Password"), c);
        c.gridy++;
        pfRegPass = new JPasswordField();
        panel.add(pfRegPass, c);

        c.gridy++;
        JButton btnRegister = UIUtils.createButton("Register", UIUtils.SUCCESS);
        panel.add(btnRegister, c);
        btnRegister.addActionListener(e -> doRegister());

        c.gridy++;
        JLabel note = new JLabel("Create your account with a strong password.");
        note.setForeground(new Color(100, 100, 100));
        panel.add(note, c);

        return wrapCard(panel);
    }

    private JPanel wrapCard(JPanel content) {
        JPanel card = UIUtils.createCardPanel();
        card.setBackground(Color.WHITE);
        card.add(content, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(18, 18, 18, 18),
                card.getBorder()
        ));
        return card;
    }

    private void doLogin() {
        try {
            String user = tfLoginUser.getText().trim();
            String pass = new String(pfLoginPass.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
                return;
            }

            User u = userDAO.findByUsername(user);
            String hashedInputPass = userDAO.hashPassword(pass);
            if (u != null && hashedInputPass.equals(u.getPassword())) {
                SwingUtilities.invokeLater(() -> {
                    MainFrame mf = new MainFrame(u);
                    mf.setVisible(true);
                });
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Login error: " + e.getMessage());
        }
    }

    private void doRegister() {
        try {
            String username = tfRegUser.getText().trim();
            String pass = new String(pfRegPass.getPassword());
            String fullName = tfRegName.getText().trim();
            if (username.isEmpty() || pass.isEmpty() || fullName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields to register.");
                return;
            }

            if (pass.length() < 6) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.");
                return;
            }

            User existing = userDAO.findByUsername(username);
            if (existing != null) {
                JOptionPane.showMessageDialog(this, "Username already exists. Choose another one.");
                return;
            }

            User u = new User();
            u.setUsername(username);
            u.setPassword(pass);
            u.setFullName(fullName);
            boolean ok = userDAO.create(u);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registration successful. You can now login.");
                
                // Clear registration fields
                tfRegName.setText("");
                tfRegUser.setText("");
                pfRegPass.setText("");
                
                // Clear and prepare login fields
                tfLoginUser.setText("");
                pfLoginPass.setText("");
                
                // Switch to login panel and focus username field
                cardLayout.show(cards, "login");
                tfLoginUser.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Registration error: " + e.getMessage());
        }
    }
}
