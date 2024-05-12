package org.example.view;

import lombok.Getter;
import org.example.controller.EmployeeMenuController;

import javax.swing.*;

@Getter
public class EmployeeMenuView implements Observer {
    private JLabel titleLabel;
    private JButton CRUDRoomsButton;
    private JButton CRUDClientsButton;
    private JButton backButton;
    private JPanel mainPanel;
    private JButton reservationButton;
    private JButton statisticsBtn;
    private final EmployeeMenuController employeeMenuController;

    {
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        this.CRUDRoomsButton = new JButton();
    }

    public EmployeeMenuView() {
        employeeMenuController = new EmployeeMenuController(this);
    }

    @Override
    public void update() {

    }
}
