package org.example.view;

import org.example.controller.MeniuAngajatViewController;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class MeniuAngajatView implements IMeniuAngajatView {
    private JLabel titleLabel;
    private JButton CRUDCamereButton;
    private JButton CRUDClientiButton;
    private JButton backButton;
    private JPanel mainPanel;
    private MeniuAngajatViewController meniuAngajatViewController;

    public MeniuAngajatView() {
        meniuAngajatViewController = new MeniuAngajatViewController(this);

        CRUDCamereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meniuAngajatViewController.CRUDCamereListener();
            }
        });
        CRUDClientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meniuAngajatViewController.CRUDClientiListener();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meniuAngajatViewController.backBtnListener();
            }
        });
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

    @Override
    public JPanel getMainPanel() {
        return this.mainPanel;
    }
}
