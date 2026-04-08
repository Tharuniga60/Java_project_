package app.dao;

import app.DBConnection;
import app.model.User;
import java.sql.*;

public class UserDAO {
public User findByUsername(String username) {
    String sql = "SELECT id, username, password_hash, full_name FROM users WHERE username = ?";
    try (Connection c = DBConnection.getConnection(); 
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setString(1, username);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password_hash")); // Stored hashed password
                u.setFullName(rs.getString("full_name"));
                return u;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Database error: " + e.getMessage());
    }
    return null;
}


public String hashPassword(String password) {
    try {
        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        return java.util.Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
        throw new RuntimeException("Failed to hash password", e);
    }
}

public boolean create(User user) {
    String sql = "INSERT INTO users (username, password_hash, full_name) VALUES (?, ?, ?)";
    try (Connection c = DBConnection.getConnection(); 
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, user.getUsername());
        ps.setString(2, hashPassword(user.getPassword())); // Now properly hashed
        ps.setString(3, user.getFullName());
        int affected = ps.executeUpdate();
        if (affected == 1) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                    return true;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Database error: " + e.getMessage());
    }
    return false;
}
}