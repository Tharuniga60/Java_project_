package app.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ResourceTableModel extends AbstractTableModel {
private final String[] cols = {"ID","Title","Description","URL","Author ID"};
private List<Resource> data;


public ResourceTableModel(List<Resource> data) { this.data = data; }
public void setData(List<Resource> data) { this.data = data; fireTableDataChanged(); }
public List<Resource> getData() { return data; }


public int getRowCount() { return data == null ? 0 : data.size(); }
public int getColumnCount() { return cols.length; }
public String getColumnName(int c) { return cols[c]; }
public Object getValueAt(int r, int c) {
Resource res = data.get(r);
switch(c) {
case 0: return res.getId();
case 1: return res.getTitle();
case 2: return res.getDescription();
case 3: return res.getUrl();
case 4: return res.getAuthorId();
}
return null;
}
}