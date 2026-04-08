package app.ui;

import app.dao.ResourceDAO;
import app.model.Resource;
import app.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class UpdateResourceDialog extends JDialog {
private JTextField tfTitle, tfUrl;
private JTextArea taDesc;
private boolean saved = false;
private ResourceDAO dao = new ResourceDAO();
private Resource resource;
private User user;


public UpdateResourceDialog(Frame owner, Resource resource, User user) {
super(owner, "Update Resource", true);
this.resource = resource;
this.user = user;
setSize(500,350);
setLocationRelativeTo(owner);
init();
}


private void init() {
JPanel p = new JPanel(new BorderLayout(6,6));
JPanel top = new JPanel(new GridLayout(3,1,4,4));
tfTitle = new JTextField(resource.getTitle());
tfUrl = new JTextField(resource.getUrl());
taDesc = new JTextArea(resource.getDescription(), 6,40);
top.add(labeled("Title", tfTitle)); top.add(labeled("URL", tfUrl)); top.add(labeled("Description", new JScrollPane(taDesc)));
p.add(top, BorderLayout.CENTER);
JPanel btns = new JPanel(); JButton bSave = new JButton("Update"); JButton bCancel = new JButton("Cancel");
btns.add(bSave); btns.add(bCancel); p.add(btns, BorderLayout.SOUTH);
bSave.addActionListener(this::save);
bCancel.addActionListener(e -> dispose());
add(p);
}


private JPanel labeled(String label, Component c) {
JPanel p = new JPanel(new BorderLayout()); p.add(new JLabel(label), BorderLayout.NORTH); p.add(c, BorderLayout.CENTER); return p;
}


private void save(ActionEvent e) {
String t = tfTitle.getText().trim(); String u = tfUrl.getText().trim(); String d = taDesc.getText().trim();
if (t.isEmpty()) { JOptionPane.showMessageDialog(this, "Title required"); return; }
resource.setTitle(t); resource.setUrl(u); resource.setDescription(d);
boolean ok = dao.update(resource);
if (ok) { saved = true; JOptionPane.showMessageDialog(this, "Resource updated"); dispose(); }
else JOptionPane.showMessageDialog(this, "Update failed");
}


public boolean isSaved() { return saved; }
}