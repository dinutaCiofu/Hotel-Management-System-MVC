package org.example.controller;

import org.example.model.entities.UserType;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.FirstPageView;
import org.example.view.LoginView;
import org.example.view.RoomsView;
import org.springframework.stereotype.Controller;

@Controller
public class FirstPageController {
    private final FirstPageView firstPageView;

    public FirstPageController(FirstPageView firstPageView){
        this.firstPageView = firstPageView;
    }

    public void changeWindow(){
        UserType selectedOption = (UserType) firstPageView.getUserTypeComboBox().getSelectedItem();
        if(selectedOption == UserType.ADMINISTRATOR || selectedOption == UserType.EMPLOYEE){
            LoginView loginView = new LoginView(selectedOption);
            GUIFrameSinglePointAccess.changePanel(loginView.getMainPanel(), "Login");
        }else{
            RoomsView roomsView = new RoomsView(selectedOption);
            GUIFrameSinglePointAccess.changePanel(roomsView.getMainJPanel(), "Rooms");
        }
    }
}
