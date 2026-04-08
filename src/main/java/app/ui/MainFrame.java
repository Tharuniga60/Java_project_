package app.ui;

import app.dao.ResourceDAO;
import app.model.Resource;
import app.model.ResourceTableModel;
import app.model.User;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.List;
import javax.swing.*;

public class MainFrame extends JFrame {
    private User user;
    private JTextField tfSearch;
    private JTable table;
    private ResourceTableModel model;
    private ResourceDAO dao = new ResourceDAO();
    
    public MainFrame(User user) {
        this.user = user;
        setTitle("Resource Manager - " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
        loadList();
    }


private void init() {
JPanel top = new JPanel(new BorderLayout(6,6));
tfSearch = new JTextField(); JButton bSearch = new JButton("Search"); JButton bAdd = new JButton("Add");
JButton bUpdate = new JButton("Update"); JButton bDelete = new JButton("Delete");
bUpdate.setBackground(Color.BLUE); bUpdate.setForeground(Color.WHITE);
bDelete.setBackground(Color.RED); bDelete.setForeground(Color.WHITE);
top.add(tfSearch, BorderLayout.CENTER); JPanel tt = new JPanel(); tt.add(bSearch); tt.add(bAdd); tt.add(bUpdate); tt.add(bDelete); top.add(tt, BorderLayout.EAST);
add(top, BorderLayout.NORTH);


model = new ResourceTableModel(List.of());
table = new JTable(model);
table.setRowHeight(24);
table.addMouseListener(new MouseAdapter() {
public void mouseClicked(MouseEvent e) {
if (e.getClickCount() == 2) {
int r = table.getSelectedRow();
if (r >= 0) {
String url = (String) model.getValueAt(r, 3);
if (url != null && !url.isBlank()) openUrl(url);
}
}
}
});
add(new JScrollPane(table), BorderLayout.CENTER);


bSearch.addActionListener(a -> doSearch());
bAdd.addActionListener(a -> doAdd());
bUpdate.addActionListener(a -> doUpdate());
bDelete.addActionListener(a -> doDelete());
}


private void loadList() {
List<Resource> list = dao.listAll();
model.setData(list);
}


private void doSearch() {
String q = tfSearch.getText().trim();
if (q.isBlank()) loadList(); else model.setData(dao.searchByTitle(q));
}


private void doAdd() {
AddResourceDialog d = new AddResourceDialog(this, user);
d.setVisible(true);
if (d.isSaved()) loadList();
}


private void doUpdate() {
int r = table.getSelectedRow();
if (r < 0) { JOptionPane.showMessageDialog(this, "Select a resource to update"); return; }
Resource res = model.getData().get(r);
if (res.getAuthorId() != user.getId()) { JOptionPane.showMessageDialog(this, "You can only update your own resources"); return; }
UpdateResourceDialog d = new UpdateResourceDialog(this, res, user);
d.setVisible(true);
if (d.isSaved()) loadList();
}


private void doDelete() {
int r = table.getSelectedRow();
if (r < 0) { JOptionPane.showMessageDialog(this, "Select a resource to delete"); return; }
Resource res = model.getData().get(r);
if (res.getAuthorId() != user.getId()) { JOptionPane.showMessageDialog(this, "You can only delete your own resources"); return; }
int opt = JOptionPane.showConfirmDialog(this, "Delete this resource?", "Confirm", JOptionPane.YES_NO_OPTION);
if (opt == JOptionPane.YES_OPTION) {
boolean ok = dao.delete(res.getId());
if (ok) { JOptionPane.showMessageDialog(this, "Resource deleted"); loadList(); }
else JOptionPane.showMessageDialog(this, "Delete failed");
}
}


private void openUrl(String url) {
try { Desktop.getDesktop().browse(new URI(url)); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Unable to open URL: "+ex.getMessage()); }
}
}