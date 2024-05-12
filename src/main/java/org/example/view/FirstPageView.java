package org.example.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;
import org.example.controller.FirstPageController;
import org.example.model.entities.UserType;
import org.example.utils.CustomLocale;
import org.example.utils.LanguageManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

@Getter
public class FirstPageView implements Observer {
    private JPanel mainPanel;
    private JLabel userTypeLabel;
    private JLabel iconLabel;
    private JComboBox<Object> userTypeComboBox;
    private JButton okBtn;
    private JLabel languageLabel;
    private JComboBox<Object> languageComboBox;
    private final FirstPageController firstPageController;

    {
        $$$setupUI$$$();
    }

    public FirstPageView() {
        userTypeComboBox.setModel(new DefaultComboBoxModel<>(UserType.values()));
        languageComboBox.setModel(new DefaultComboBoxModel<>(CustomLocale.values()));
        firstPageController = new FirstPageController(this);
        userTypeLabel.setText(LanguageManager.getString("userTypeLabel"));
        languageLabel.setText(LanguageManager.getString("languageLabel"));
    }

    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(4, 1, new Insets(20, 10, 20, 10), -1, -1));
        mainPanel.setBackground(new Color(-8617787));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        userTypeLabel = new JLabel();
        userTypeLabel.setForeground(new Color(-16760389));
        mainPanel.add(userTypeLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/hotel.png"))));
        iconLabel.setText("");
        mainPanel.add(iconLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userTypeComboBox = new JComboBox<>();
        userTypeComboBox.setBackground(new Color(-6308366));
        mainPanel.add(userTypeComboBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        okBtn = new JButton();
        okBtn.setBackground(new Color(-16760389));
        okBtn.setText("OK");
        mainPanel.add(okBtn, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    @Override
    public void update() {

    }
}
