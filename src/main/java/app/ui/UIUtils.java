package app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UIUtils {
    // Color constants
    public static final Color PRIMARY = new Color(33, 150, 243);
    public static final Color PRIMARY_DARK = new Color(21, 101, 192);
    public static final Color SURFACE = new Color(245, 245, 245);
    public static final Color SUCCESS = new Color(76, 175, 80);
    public static final Color ERROR = new Color(244, 67, 54);
    public static final Color WARNING = new Color(255, 193, 7);

    /**
     * Creates a styled button with the specified text and background color.
     */
    public static JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setOpaque(true);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Creates a styled card panel with a border layout.
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        return panel;
    }
}
