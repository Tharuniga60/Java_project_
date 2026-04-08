import app.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryUsers {
    public static void main(String[] args) throws Exception {
        try (Connection c = DBConnection.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT id, username, password_hash, full_name, created_at FROM users")) {
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %s\n", rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"), rs.getString("full_name"), rs.getTimestamp("created_at"));
            }
        }
    }
}
