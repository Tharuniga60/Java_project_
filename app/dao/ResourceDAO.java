package app.dao;

import app.DBConnection;
import app.model.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResourceDAO {
public List<Resource> listAll() {
List<Resource> list = new ArrayList<>();
String sql = "SELECT id, title, description, url, author_id FROM resources ORDER BY created_at DESC";
try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
Resource r = new Resource();
r.setId(rs.getInt("id"));
r.setTitle(rs.getString("title"));
r.setDescription(rs.getString("description"));
r.setUrl(rs.getString("url"));
r.setAuthorId(rs.getInt("author_id"));
list.add(r);
}
} catch (SQLException e) { e.printStackTrace(); }
return list;
}


public boolean add(Resource r) {
String sql = "INSERT INTO resources (title, description, url, author_id) VALUES (?, ?, ?, ?)";
try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
ps.setString(1, r.getTitle());
ps.setString(2, r.getDescription());
ps.setString(3, r.getUrl());
if (r.getAuthorId() > 0) ps.setInt(4, r.getAuthorId()); else ps.setNull(4, Types.INTEGER);
int a = ps.executeUpdate();
return a == 1;
} catch (SQLException e) { e.printStackTrace(); }
return false;
}


public List<Resource> searchByTitle(String q) {
List<Resource> list = new ArrayList<>();
String sql = "SELECT id, title, description, url, author_id FROM resources WHERE title LIKE ? ORDER BY created_at DESC";
try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
ps.setString(1, "%" + q + "%");
try (ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
Resource r = new Resource();
r.setId(rs.getInt("id"));
r.setTitle(rs.getString("title"));
r.setDescription(rs.getString("description"));
r.setUrl(rs.getString("url"));
r.setAuthorId(rs.getInt("author_id"));
list.add(r);
}
}
} catch (SQLException e) { e.printStackTrace(); }
return list;
}


public Resource getById(int id) {
String sql = "SELECT id, title, description, url, author_id FROM resources WHERE id=?";
try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
ps.setInt(1, id);
try (ResultSet rs = ps.executeQuery()) {
if (rs.next()) {
Resource r = new Resource();
r.setId(rs.getInt("id"));
r.setTitle(rs.getString("title"));
r.setDescription(rs.getString("description"));
r.setUrl(rs.getString("url"));
r.setAuthorId(rs.getInt("author_id"));
return r;
}
}
} catch (SQLException e) { e.printStackTrace(); }
return null;
}


public boolean update(Resource r) {
String sql = "UPDATE resources SET title=?, description=?, url=? WHERE id=?";
try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
ps.setString(1, r.getTitle());
ps.setString(2, r.getDescription());
ps.setString(3, r.getUrl());
ps.setInt(4, r.getId());
int a = ps.executeUpdate();
return a == 1;
} catch (SQLException e) { e.printStackTrace(); }
return false;
}


public boolean delete(int id) {
String sql = "DELETE FROM resources WHERE id=?";
try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
ps.setInt(1, id);
int a = ps.executeUpdate();
return a == 1;
} catch (SQLException e) { e.printStackTrace(); }
return false;
}
}