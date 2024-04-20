package org.example.controller;

import org.example.model.entities.UserType;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.CrudUsersView;
import org.example.view.FirstPageView;
import org.example.view.AdminMenuView;
import org.example.view.RoomsView;
import org.springframework.stereotype.Controller;

@Controller
public class AdminMenuController {
    private final AdminMenuView adminMenuView;

    public AdminMenuController(AdminMenuView adminMenuView){
        this.adminMenuView = adminMenuView;
    }

    public void CRUDRoomsListener(){
        RoomsView roomsView = new RoomsView(UserType.ADMINISTRATOR);
        GUIFrameSinglePointAccess.changePanel(roomsView.getMainPanel(), "Rooms");
    }

    public void CRUDUsersListener(){
        CrudUsersView crudUsersView = new CrudUsersView();
        GUIFrameSinglePointAccess.changePanel(crudUsersView.getPanel(),"CRUD Users");
    }

    public void backBtnListener(){
        FirstPageView firstPageView = new FirstPageView();
        GUIFrameSinglePointAccess.changePanel(firstPageView.getMainPanel(), "First page");
    }
}
