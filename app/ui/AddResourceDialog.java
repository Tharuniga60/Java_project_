package app.ui;

import app.dao.ResourceDAO;
import app.model.Resource;
import app.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AddResourceDialog extends JDialog {
private JTextField tfTitle, tfUrl;
private JTextArea taDesc;
private boolean saved = false;
private ResourceDAO dao = new ResourceDAO();
private User user;


public AddResourceDialog(Frame owner, User user) {
super(owner, "Add Resource", true);
this.user = user;
setSize(500,350);
setLocationRelativeTo(owner);
init();
}


private void init() {
JPanel p = new JPanel(new BorderLayout(6,6));
JPanel top = new JPanel(new GridLayout(3,1,4,4));
tfTitle = new JTextField(); tfUrl = new JTextField(); taDesc = new JTextArea(6,40);
top.add(labeled("Title", tfTitle)); top.add(labeled("URL", tfUrl)); top.add(labeled("Description", new JScrollPane(taDesc)));
p.add(top, BorderLayout.CENTER);
JPanel btns = new JPanel(); JButton bSave = new JButton("Save"); JButton bCancel = new JButton("Cancel");
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
Resource r = new Resource(); r.setTitle(t); r.setUrl(u); r.setDescription(d); r.setAuthorId(user.getId());
boolean ok = dao.add(r);
if (ok) { saved = true; JOptionPane.showMessageDialog(this, "Resource saved"); dispose(); }
else JOptionPane.showMessageDialog(this, "Save failed");
}


public boolean isSaved() { return saved; }
}