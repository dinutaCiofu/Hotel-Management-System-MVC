package org.example.controller;

import org.example.model.entities.User;
import org.example.model.entities.UserType;
import org.example.model.repository.UserRepository;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.AdminMenuView;
import org.example.view.EmployeeMenuView;
import org.example.view.LoginView;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LoginController {
    private final UserRepository userRepository;
    private final LoginView loginView;

    public LoginController(LoginView loginView){
        this.loginView = loginView;
        this.userRepository = new UserRepository();
    }

    public void login(UserType userLogged){
        List<User> users = userRepository.readAll();
        String email = loginView.getEmailTextField().getText();
        String password = new String(loginView.getPasswordField().getPassword());

        for(User user : users) {
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                if(userLogged == UserType.EMPLOYEE) {
                    EmployeeMenuView employeeMenuView = new EmployeeMenuView();
                    GUIFrameSinglePointAccess.changePanel(employeeMenuView.getMainPanel(), "Menu");
                } else if (userLogged == UserType.ADMINISTRATOR) {
                    AdminMenuView adminMenuView = new AdminMenuView();
                    GUIFrameSinglePointAccess.changePanel(adminMenuView.getMainJPanel(), "Menu");
                }
            }
        }
    }
}
