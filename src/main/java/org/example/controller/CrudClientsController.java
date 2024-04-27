package org.example.controller;

import org.example.model.entities.User;
import org.example.model.entities.UserType;
import org.example.model.repository.UserRepository;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.CrudClientsView;
import org.example.view.FirstPageView;
import org.springframework.stereotype.Controller;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CrudClientsController {
    private final UserRepository userRepository;
    private final CrudClientsView crudClientsView;

    public CrudClientsController(CrudClientsView crudClientsView) {
        this.crudClientsView = crudClientsView;
        this.userRepository = new UserRepository();
        crudClientsView.getAddClientButton().addActionListener(e -> this.addClient());
        crudClientsView.getUpdateClientButton().addActionListener(e -> this.updateClient());
        crudClientsView.getBackButton().addActionListener(e -> this.backBtnListener());
        crudClientsView.getDeleteClientButton().addActionListener(e -> this.deleteClient());
    }

    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = List.of("ID", "name", "email", "password");
        model.setRowCount(0);
        model = new DefaultTableModel() {};

        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    public void setTableRows(DefaultTableModel model, List<User> users) {
        for (User user : users) {
            Integer id = user.getId();
            String name = user.getName();
            String email = user.getEmail();
            String password = user.getPassword();
            model.addRow(new Object[]{id, name, email, password});
        }
    }

    public void populateTable() {
        DefaultTableModel model = setTableColumns();
        List<User> clients = userRepository.readAll().stream()
                .filter(user -> user.getUserType() == UserType.CLIENT)
                .collect(Collectors.toList());
        setTableRows(model, clients);
        crudClientsView.getTableClients().setModel(model);
    }

    public void addClient() {
        String name = crudClientsView.getName().getText();
        String email = crudClientsView.getEmail().getText();
        String password = crudClientsView.getPassword().getText();
        User client = userRepository.findByEmail(email);
        if (client == null) {
            client = new User();
            client.setUserType(UserType.CLIENT);
            client.setName(name);
            client.setEmail(email);
            client.setPassword(password);
            userRepository.save(client);
            crudClientsView.getEmail().setText("");
            crudClientsView.getName().setText("");
            crudClientsView.getPassword().setText("");
            populateTable();
        } else {
            System.out.println("Client existing already!");
        }
    }

    public void updateClient() {
        DefaultTableModel model = (DefaultTableModel) crudClientsView.getTableClients().getModel();
        int selectedRow = crudClientsView.getTableClients().getSelectedRow();
        if (selectedRow != -1) {
            Integer id = (Integer) model.getValueAt(selectedRow, 0);
            String name = (String) model.getValueAt(selectedRow, 1);
            String email = (String) model.getValueAt(selectedRow, 2);
            String password = (String) model.getValueAt(selectedRow, 3);
            User client = userRepository.findById(id);
            if (client != null) {
                client.setName(name);
                client.setEmail(email);
                client.setPassword(password);
                userRepository.update(client);
                populateTable();
            } else {
                System.out.println("Client not found.");
            }
        }
    }

    public void backBtnListener() {
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }

    public void deleteClient() {
        DefaultTableModel model = (DefaultTableModel) crudClientsView.getTableClients().getModel();
        int selectedRow = crudClientsView.getTableClients().getSelectedRow();
        if (selectedRow != -1) {
            Integer id = (Integer) model.getValueAt(selectedRow, 0);
            User client = userRepository.findById(id);
            if (client != null) {
                userRepository.delete(client);
                model.removeRow(selectedRow);
            } else {
                System.out.println("Client not found.");
            }
        }
        populateTable();
    }
}
