import javax.swing.SwingUtilities;

import app.ui.LoginFrame;

public class Main {
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> {
LoginFrame lf = new LoginFrame();
lf.setVisible(true);
});
}
}