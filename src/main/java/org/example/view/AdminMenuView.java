package org.example.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;
import org.example.controller.AdminMenuController;

import javax.swing.*;
import java.awt.*;

@Getter
public class AdminMenuView implements Observer{
    private JPanel mainJPanel;
    private JLabel titleLabel;
    private JButton CRUDUsersButton;
    private JButton backButton;
    private final AdminMenuController adminMenuController;

    public AdminMenuView() {
        adminMenuController = new AdminMenuController(this);
    }

    {
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        mainJPanel = new JPanel();
        mainJPanel.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));
        mainJPanel.setBackground(new Color(-9144129));
        titleLabel = new JLabel();
        titleLabel.setForeground(new Color(-16760389));
        titleLabel.setText("Select an action");
        mainJPanel.add(titleLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        CRUDRoomsButton = new JButton();
//        CRUDRoomsButton.setBackground(new Color(-6308366));
//        CRUDRoomsButton.setText("CRUD rooms");
//        mainJPanel.add(CRUDRoomsButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CRUDUsersButton = new JButton();
        CRUDUsersButton.setBackground(new Color(-6308366));
        CRUDUsersButton.setText("CRUD users");
        mainJPanel.add(CRUDUsersButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setBackground(new Color(-6308366));
        backButton.setText("Back");
        mainJPanel.add(backButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return mainJPanel;
    }

    @Override
    public void update() {

    }
}
