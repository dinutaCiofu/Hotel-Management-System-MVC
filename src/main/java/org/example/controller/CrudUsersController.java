package org.example.controller;

import org.example.model.entities.UserTypeMapper;
import org.example.model.entities.User;
import org.example.model.entities.UserType;
import org.example.model.repository.UserRepository;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.CrudUsersView;
import org.example.view.FirstPageView;
import org.springframework.stereotype.Controller;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class CrudUsersController {
    private final UserRepository userRepository;
    private final CrudUsersView crudUsersView;

    public CrudUsersController(CrudUsersView crudUsersView) {
        this.crudUsersView = crudUsersView;
        this.userRepository = new UserRepository();
        populateTable();
    }

    public void backBtnListener() {
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }

    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = List.of("ID", "name", "email", "password", "user type");
        model.setRowCount(0);
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    public void setTableRows(DefaultTableModel model, List<User> users) {
        for (User user : users) {
            UUID id = user.getId();
            String name = user.getName();
            String email = user.getEmail();
            String password = user.getPassword();
            String userType = UserTypeMapper.mapToUserTypeString(user.getUserType());
            model.addRow(new Object[]{id, name, email, password, userType});
        }
    }

    public DefaultTableModel populateTable() {
        DefaultTableModel model = setTableColumns();
        List<User> users = userRepository.readAll().stream()
                .filter(user -> user.getUserType() == UserType.EMPLOYEE || user.getUserType() == UserType.ADMINISTRATOR)
                .collect(Collectors.toList());
        setTableRows(model, users);
        crudUsersView.setTable(model);
        return model;
    }

    public void addUser() {
        String name = crudUsersView.getName();
        String email = crudUsersView.getEmail();
        String password = crudUsersView.getPassword();
        UserType userType = UserTypeMapper.mapToUserType(crudUsersView.getUserType());
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setUserType(UserType.CLIENT);
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setUserType(userType);
            userRepository.save(user);
            crudUsersView.setEmail("");
            crudUsersView.setName("");
            crudUsersView.setPassword("");
            crudUsersView.setUserType("");
            populateTable();
        } else {
            System.out.println("User existing already!");
        }
    }

    public void updateUser() {
        DefaultTableModel model = (DefaultTableModel) crudUsersView.getTable().getModel();
        int selectedRow = crudUsersView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            String name = (String) model.getValueAt(selectedRow, 1);
            String email = (String) model.getValueAt(selectedRow, 2);
            String password = (String) model.getValueAt(selectedRow, 3);
            String userType = (String) model.getValueAt(selectedRow, 4);
            User user = userRepository.findById(id);
            if (user != null) {
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setUserType(UserTypeMapper.mapToUserType(userType));
                userRepository.update(user);
            } else {
                System.out.println("User not found.");
            }
        }
    }

    public void deleteUser() {
        DefaultTableModel model = (DefaultTableModel) crudUsersView.getTable().getModel();
        int selectedRow = crudUsersView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            User user = userRepository.findById(id);
            if (user != null) {
                userRepository.delete(user);
                model.removeRow(selectedRow);
            } else {
                System.out.println("User not found.");
            }
        }
    }
}
