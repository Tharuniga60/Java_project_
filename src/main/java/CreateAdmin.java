import app.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.security.MessageDigest;
import java.util.Base64;

public class CreateAdmin {
    private static String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            // First, try to delete existing admin user
            String deleteSql = "DELETE FROM users WHERE username = 'admin'";
            conn.createStatement().executeUpdate(deleteSql);

            // Now create new admin user with hashed password
            String password = "Admin@123"; // Strong password
            String hashedPassword = hashPassword(password);
            
            String sql = "INSERT INTO users (username, password_hash, full_name) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "admin");
            stmt.setString(2, hashedPassword);
            stmt.setString(3, "System Administrator");
            stmt.executeUpdate();
            
            System.out.println("Admin user created successfully!");
            System.out.println("=================================");
            System.out.println("Username: admin");
            System.out.println("Password: " + password);
            System.out.println("=================================");
            System.out.println("Please save these credentials securely.");
        } catch (Exception e) {
            System.err.println("Error creating admin user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}