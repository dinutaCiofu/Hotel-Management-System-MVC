package org.example.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lombok.Getter;
import org.example.controller.LoginController;
import org.example.model.entities.UserType;
import org.example.utils.LanguageManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Getter
public class LoginView implements Observer {
    private JPanel mainPanel;
    private JLabel iconLabel;
    private JPanel formPanel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private final LoginController loginController;
    private final UserType userLogged;

    public LoginView(UserType userType) {
        this.userLogged = userType;
        loginController = new LoginController(this);
        loginButton.addActionListener(e -> loginController.login(userLogged));
        emailLabel.setText(LanguageManager.getString("emailLabel"));
        passwordLabel.setText(LanguageManager.getString("passwordLabel"));
    }

    {
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-8617787));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        mainPanel.setBackground(new Color(-8617787));
        panel1.add(mainPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/login.png"))));
        iconLabel.setText("");
        mainPanel.add(iconLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayoutManager(3, 2, new Insets(20, 30, 10, 30), -1, -1));
        formPanel.setBackground(new Color(-9144129));
        mainPanel.add(formPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        emailLabel = new JLabel();
        emailLabel.setForeground(new Color(-16760389));
        formPanel.add(emailLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordLabel = new JLabel();
        passwordLabel.setForeground(new Color(-16760389));
        formPanel.add(passwordLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailTextField = new JTextField();
        emailTextField.setBackground(new Color(-6308366));
        formPanel.add(emailTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(-6308366));
        passwordField.setEchoChar('*');
        formPanel.add(passwordField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loginButton = new JButton();
        loginButton.setBackground(new Color(-9144129));
        loginButton.setHideActionText(false);
        loginButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/loginBtn.png"))));
        loginButton.setOpaque(false);
        loginButton.setText("");
        formPanel.add(loginButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    @Override
    public void update() {

    }
}
