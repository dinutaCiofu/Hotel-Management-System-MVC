package org.example.view;

import org.example.controller.EmployeeMenuController;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class EmployeeMenuView implements Observer {
    private JLabel titleLabel;
    private JButton CRUDRoomsButton;
    private JButton CRUDClientsButton;
    private JButton backButton;
    private JPanel mainPanel;
    private final EmployeeMenuController employeeMenuController;

    public EmployeeMenuView() {
        employeeMenuController = new EmployeeMenuController(this);
        CRUDRoomsButton.addActionListener(e -> employeeMenuController.CRUDRoomsListener());
        CRUDClientsButton.addActionListener(e -> employeeMenuController.CRUDClientsListener());
        backButton.addActionListener(e -> employeeMenuController.backBtnListener());
    }

    /**
     * @noinspection ALL
     */
    private Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    @Override
    public void update() {

    }
}
