package org.example.controller;

import org.example.model.entities.TipUtilizator;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.IFirstPage;
import org.example.view.LoginView;
import org.example.view.RoomsView;
import org.springframework.stereotype.Service;

@Service
public class FirstPageController {
    private IFirstPage iFirstPage;

    public FirstPageController(IFirstPage iFirstPage){
        this.iFirstPage = iFirstPage;
    }
    public void changeWindow(){
        TipUtilizator selectedOption = iFirstPage.getSelectedOption();
        if(selectedOption == TipUtilizator.ADMINISTRATOR || selectedOption == TipUtilizator.ANGAJAT){
            LoginView loginView = new LoginView(selectedOption);
            GUIFrameSinglePointAccess.changePanel(loginView.getMainPanel(), "Login");
        }else{
            RoomsView roomsView = new RoomsView(selectedOption);
            GUIFrameSinglePointAccess.changePanel(roomsView.getMainPanel(), "Rooms");
        }
    }
}
