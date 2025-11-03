package app.ui;

import app.dao.UserDAO;
import app.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField tfUser;
    private JPasswordField pfPass;
    private UserDAO userDAO = new UserDAO();
    
    public LoginFrame() {
        setSize(400,220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }


private void init() {
JPanel p = new JPanel(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
c.insets = new Insets(6,6,6,6);
c.fill = GridBagConstraints.HORIZONTAL;


c.gridx=0; c.gridy=0; p.add(new JLabel("Username:"), c);
c.gridx=1; tfUser = new JTextField(18); p.add(tfUser, c);
c.gridx=0; c.gridy=1; p.add(new JLabel("Password:"), c);
c.gridx=1; pfPass = new JPasswordField(18); p.add(pfPass, c);


JButton btnLogin = new JButton("Login");
JButton btnRegister = new JButton("Register");


c.gridx=0; c.gridy=2; p.add(btnLogin, c);
c.gridx=1; p.add(btnRegister, c);


btnLogin.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
doLogin();
}
});
btnRegister.addActionListener(a -> doRegister());


add(p);
}


private void doLogin() {
    try {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());
        
        if (user.isEmpty() || pass.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Enter username and password"); 
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
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    } catch (RuntimeException e) {
        JOptionPane.showMessageDialog(this, "Login error: " + e.getMessage());
    }
}


private void doRegister() {
    try {
        String user = tfUser.getText().trim();
        String pass = new String(pfPass.getPassword());
        
        if (user.isEmpty() || pass.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Fill both fields"); 
            return; 
        }
        
        // Validate password strength
        if (pass.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long");
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
        if (ok) {
            JOptionPane.showMessageDialog(this, "Registered successfully. You can now login.");
            tfUser.setText("");
            pfPass.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
        }
    } catch (RuntimeException e) {
        JOptionPane.showMessageDialog(this, "Registration error: " + e.getMessage());
    }
}

}